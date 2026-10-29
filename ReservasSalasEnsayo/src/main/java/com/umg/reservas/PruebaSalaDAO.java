
package com.umg.reservas;

import com.umg.reservas.dao.SalaDAO;
import com.umg.reservas.modelo.Sala;

import java.sql.SQLException;
import java.util.List;

public class PruebaSalaDAO {

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println(" SISTEMA DE RESERVAS DE ENSAYO");
        System.out.println("==================================");

        SalaDAO salaDAO = new SalaDAO();

        try {
            // Consultar todas las salas
            List<Sala> salas = salaDAO.listarSalas();

            System.out.println("\nSALAS REGISTRADAS:");
            System.out.println("Total de salas: " + salas.size());

            for (Sala sala : salas) {
                sala.mostrarInformacion();
            }

            // Buscar una sala por ID
            System.out.println("\nBUSQUEDA DE SALA POR ID:");

            Sala salaBuscada = salaDAO.buscarPorId(1);

            if (salaBuscada != null) {
                System.out.println("Sala encontrada:");
                salaBuscada.mostrarInformacion();
            } else {
                System.out.println("No existe la sala.");
            }

            System.out.println("\nPrueba finalizada correctamente.");

        } catch (SQLException e) {
            System.out.println("ERROR AL CONSULTAR POSTGRESQL:");
            System.out.println(e.getMessage());
        }
    }
}
