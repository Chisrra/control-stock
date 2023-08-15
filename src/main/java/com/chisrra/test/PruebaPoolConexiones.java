package com.chisrra.test;

import com.chisrra.DatabaseConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaPoolConexiones {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 20; i++) {
                Connection connection = DatabaseConnector.getConnection();
                System.out.println("Conexión " + (i + 1) + " abierta");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
