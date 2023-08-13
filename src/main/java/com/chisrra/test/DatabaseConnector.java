package com.chisrra.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    public static Connection createConnection() throws SQLException, IOException {
        Properties properties = loadProperties("src/config.properties");
        String dbUrl = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");

        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    private static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }
        return properties;
    }
}
