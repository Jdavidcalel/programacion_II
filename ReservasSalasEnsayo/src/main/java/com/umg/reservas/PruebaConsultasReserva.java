package com.umg.reservas;

import com.umg.reservas.dao.ReservaDAO;
import com.umg.reservas.modelo.Reserva;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PruebaConsultasReserva {

    public static void main(String[] args) {

        ReservaDAO reservaDAO = new ReservaDAO();

        // Fecha de la reserva registrada anteriormente
        LocalDate fecha = LocalDate.of(2026, 10, 30);

        try {
            System.out.println("================================");
            System.out.println(" CONSULTAS DE RESERVAS");
            System.out.println("================================");

            // CONSULTA 1: Buscar por fecha
            System.out.println("\nRESERVAS POR FECHA:");

            List<Reserva> reservasFecha =
                    reservaDAO.buscarPorFecha(fecha);

            System.out.println(
                    "Total encontradas: " + reservasFecha.size()
            );

            for (Reserva reserva : reservasFecha) {
                reserva.mostrarInformacion();
            }

            // CONSULTA 2: Buscar por cliente
            System.out.println("\nRESERVAS POR CLIENTE:");

            List<Reserva> reservasCliente =
                    reservaDAO.buscarPorCliente("Rockeros");

            System.out.println(
                    "Total encontradas: " + reservasCliente.size()
            );

            for (Reserva reserva : reservasCliente) {
                reserva.mostrarInformacion();
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error de base de datos: " + e.getMessage()
            );
        }
    }
}
