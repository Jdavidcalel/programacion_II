package com.umg.reservas;
import com.umg.reservas.conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class PruebaConexion {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println(" SISTEMA DE RESERVAS DE ENSAYO");
        System.out.println("=================================");
        String sql = "SELECT id_sala, nombre, capacidad, "
                + "tarifa_hora, estado FROM salas "
                + "ORDER BY id_sala";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta =
                     conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {
            System.out.println("Conexion exitosa a PostgreSQL.");
            System.out.println("\nSALAS REGISTRADAS:");
            System.out.println("---------------------------------");
            while (resultado.next()) {
                System.out.println(
                        "ID: " + resultado.getInt("id_sala")
                );
                System.out.println(
                        "Nombre: " + resultado.getString("nombre")
                );
                System.out.println(
                        "Capacidad: " + resultado.getInt("capacidad")
                );
                System.out.println(
                        "Tarifa: Q" + resultado.getBigDecimal("tarifa_hora")
                );
                System.out.println(
                        "Estado: " + resultado.getString("estado")
                );
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("ERROR DE CONEXION O CONSULTA:");
            System.out.println(e.getMessage());
        }
    }
}