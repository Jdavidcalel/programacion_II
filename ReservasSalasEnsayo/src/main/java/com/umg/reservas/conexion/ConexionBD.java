
package com.umg.reservas.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/reservas_ensayo";

    private static final String USUARIO = "postgres";

    private static final String PASSWORD = "Soporte14*";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                URL, USUARIO, PASSWORD
        );
    }
}

