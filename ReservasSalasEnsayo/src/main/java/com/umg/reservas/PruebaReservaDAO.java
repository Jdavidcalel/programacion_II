
package com.umg.reservas;

import com.umg.reservas.dao.ReservaDAO;
import com.umg.reservas.modelo.Reserva;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PruebaReservaDAO {

    public static void main(String[] args) {

        ReservaDAO reservaDAO = new ReservaDAO();

        // Usamos mañana para evitar fechas pasadas
        LocalDate fecha = LocalDate.now().plusDays(1);

        System.out.println("=================================");
        System.out.println(" PRUEBA DEL SISTEMA DE RESERVAS");
        System.out.println("=================================");

        // PRUEBA 1: Reserva válida
        try {
            System.out.println("\nPRUEBA 1: RESERVA VALIDA");

            boolean registrada = reservaDAO.registrarReserva(
                    1,
                    "Banda Los Rockeros",
                    fecha,
                    LocalTime.of(14, 0),
                    LocalTime.of(17, 0)
            );

            if (registrada) {
                System.out.println(
                        "Reserva registrada correctamente."
                );
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Resultado: " + e.getMessage());
        }

        // PRUEBA 2: Horario traslapado
        try {
            System.out.println("\nPRUEBA 2: HORARIO TRASLAPADO");

            reservaDAO.registrarReserva(
                    1,
                    "Banda Los Electricos",
                    fecha,
                    LocalTime.of(15, 0),
                    LocalTime.of(16, 0)
            );

            System.out.println(
                    "ATENCION: Se registro una reserva traslapada."
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Conflicto detectado correctamente:"
            );
            System.out.println(e.getMessage());

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }

        // PRUEBA 3: Consultar reservas
        try {
            System.out.println("\nPRUEBA 3: LISTAR RESERVAS");

            List<Reserva> reservas =
                    reservaDAO.listarReservas();

            System.out.println(
                    "Total de reservas: " + reservas.size()
            );

            for (Reserva reserva : reservas) {
                reserva.mostrarInformacion();
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al consultar: " + e.getMessage()
            );
        }
    }
}

