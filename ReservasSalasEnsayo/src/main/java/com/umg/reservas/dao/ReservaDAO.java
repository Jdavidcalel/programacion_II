
package com.umg.reservas.dao;

import com.umg.reservas.conexion.ConexionBD;
import com.umg.reservas.modelo.Reserva;
import com.umg.reservas.modelo.Sala;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    // ==========================================
    // 1. CONSULTAR TODAS LAS RESERVAS
    // ==========================================

    public List<Reserva> listarReservas()
            throws SQLException {

        List<Reserva> reservas = new ArrayList<>();

        String sql = """
                SELECT id_reserva, id_sala, cliente,
                       fecha, hora_inicio, hora_fin,
                       costo_total, estado
                FROM reservas
                ORDER BY fecha, hora_inicio
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reservas.add(convertirReserva(rs));
            }
        }

        return reservas;
    }

    // ==========================================
    // 2. REGISTRAR UNA NUEVA RESERVA
    // ==========================================

    public boolean registrarReserva(
            int idSala,
            String cliente,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) throws SQLException {

        // Validar nombre del cliente
        if (cliente == null || cliente.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del cliente es obligatorio."
            );
        }

        // Validar fecha
        if (fecha == null ||
                fecha.isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "La fecha no puede ser anterior a hoy."
            );
        }

        // Validar horario
        if (horaInicio == null ||
                horaFin == null ||
                !horaFin.isAfter(horaInicio)) {

            throw new IllegalArgumentException(
                    "La hora final debe ser posterior "
                            + "a la hora inicial."
            );
        }

        // Evitar horarios que ya pasaron
        if (fecha.equals(LocalDate.now()) &&
                !horaInicio.isAfter(LocalTime.now())) {

            throw new IllegalArgumentException(
                    "La hora de inicio debe ser futura."
            );
        }

        // Buscar sala en PostgreSQL
        SalaDAO salaDAO = new SalaDAO();
        Sala sala = salaDAO.buscarPorId(idSala);

        if (sala == null) {
            throw new IllegalArgumentException(
                    "La sala seleccionada no existe."
            );
        }

        if (!"ACTIVA".equals(sala.getEstado())) {
            throw new IllegalArgumentException(
                    "La sala seleccionada no esta activa."
            );
        }

        // Crear objeto Reserva
        Reserva reserva = new Reserva(
                0,
                idSala,
                cliente.trim(),
                fecha,
                horaInicio,
                horaFin,
                BigDecimal.ZERO,
                "ACTIVA"
        );

        // Calcular costo automaticamente
        BigDecimal costoTotal =
                reserva.calcularCosto(
                        sala.getTarifaHora()
                );

        // Guardar reserva en PostgreSQL
        String sql = """
                INSERT INTO reservas
                (id_sala, cliente, fecha,
                 hora_inicio, hora_fin,
                 costo_total, estado)
                VALUES (?, ?, ?, ?, ?, ?, 'ACTIVA')
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, idSala);
            ps.setString(2, cliente.trim());
            ps.setObject(3, fecha);
            ps.setObject(4, horaInicio);
            ps.setObject(5, horaFin);
            ps.setBigDecimal(6, costoTotal);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            // PostgreSQL: exclusion_violation
            // Se detecto un horario traslapado
            if ("23P01".equals(e.getSQLState())) {

                throw new IllegalArgumentException(
                        "No se puede registrar la reserva. "
                                + "La sala ya esta ocupada "
                                + "en ese horario.",
                        e
                );
            }

            throw e;
        }
    }

    // ==========================================
    // 3. CONSULTAR RESERVAS POR FECHA
    // ==========================================

    public List<Reserva> buscarPorFecha(LocalDate fecha)
            throws SQLException {

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "Debe indicar una fecha valida."
            );
        }

        List<Reserva> reservas = new ArrayList<>();

        String sql = """
                SELECT id_reserva, id_sala, cliente,
                       fecha, hora_inicio, hora_fin,
                       costo_total, estado
                FROM reservas
                WHERE fecha = ?
                ORDER BY hora_inicio
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setObject(1, fecha);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    reservas.add(convertirReserva(rs));
                }
            }
        }

        return reservas;
    }

    // ==========================================
    // 4. CONSULTAR RESERVAS POR CLIENTE
    // ==========================================

    public List<Reserva> buscarPorCliente(String cliente)
            throws SQLException {

        if (cliente == null || cliente.isBlank()) {
            throw new IllegalArgumentException(
                    "Debe indicar el nombre del cliente."
            );
        }

        List<Reserva> reservas = new ArrayList<>();

        String sql = """
                SELECT id_reserva, id_sala, cliente,
                       fecha, hora_inicio, hora_fin,
                       costo_total, estado
                FROM reservas
                WHERE cliente ILIKE ?
                ORDER BY fecha, hora_inicio
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(
                    1, "%" + cliente.trim() + "%"
            );

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    reservas.add(convertirReserva(rs));
                }
            }
        }

        return reservas;
    }

    // ==========================================
    // 5. CONVERTIR RESULTADOS SQL A OBJETOS
    // ==========================================

    private Reserva convertirReserva(ResultSet rs)
            throws SQLException {

        return new Reserva(
                rs.getInt("id_reserva"),
                rs.getInt("id_sala"),
                rs.getString("cliente"),
                rs.getDate("fecha").toLocalDate(),
                rs.getTime("hora_inicio").toLocalTime(),
                rs.getTime("hora_fin").toLocalTime(),
                rs.getBigDecimal("costo_total"),
                rs.getString("estado")
        );
    }
}
