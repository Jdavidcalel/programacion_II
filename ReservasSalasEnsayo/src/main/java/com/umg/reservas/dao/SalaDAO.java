
package com.umg.reservas.dao;

import com.umg.reservas.conexion.ConexionBD;
import com.umg.reservas.modelo.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    // Consultar todas las salas registradas
    public List<Sala> listarSalas() throws SQLException {

        List<Sala> salas = new ArrayList<>();

        String sql = """
                SELECT id_sala, nombre, capacidad,
                       tarifa_hora, estado
                FROM salas
                ORDER BY id_sala
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Sala sala = new Sala(
                        rs.getInt("id_sala"),
                        rs.getString("nombre"),
                        rs.getInt("capacidad"),
                        rs.getBigDecimal("tarifa_hora"),
                        rs.getString("estado")
                );

                salas.add(sala);
            }
        }

        return salas;
    }

    // Buscar una sala por su ID
    public Sala buscarPorId(int idSala) throws SQLException {

        String sql = """
                SELECT id_sala, nombre, capacidad,
                       tarifa_hora, estado
                FROM salas
                WHERE id_sala = ?
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, idSala);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Sala(
                            rs.getInt("id_sala"),
                            rs.getString("nombre"),
                            rs.getInt("capacidad"),
                            rs.getBigDecimal("tarifa_hora"),
                            rs.getString("estado")
                    );
                }
            }
        }

        return null;
    }

    // Registrar una nueva sala
    public boolean registrarSala(Sala sala)
            throws SQLException {

        String sql = """
                INSERT INTO salas
                (nombre, capacidad, tarifa_hora, estado)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, sala.getNombre());
            ps.setInt(2, sala.getCapacidad());
            ps.setBigDecimal(3, sala.getTarifaHora());
            ps.setString(4, sala.getEstado());

            return ps.executeUpdate() > 0;
        }
    }
}

