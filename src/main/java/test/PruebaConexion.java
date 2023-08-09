package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PruebaConexion {
    public static void main(String[] args)  {
        try {
            Properties properties = loadProperties("src/config.properties");
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");

            try(Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
                System.out.println("Conexión exitosa");
            }catch (SQLException e) {
                System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de propiedades: " + e.getMessage());
        }

    }

    private static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }
        return properties;
    }
}
