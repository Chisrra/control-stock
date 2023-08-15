package com.chisrra;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static ComboPooledDataSource dataSources;

    static {
        try {
            dataSources = new ComboPooledDataSource();
            Properties properties = loadProperties("src/config.properties");
            dataSources.setJdbcUrl( properties.getProperty("db.url") );
            dataSources.setUser( properties.getProperty("db.username") );
            dataSources.setPassword( properties.getProperty("db.password") );

            dataSources.setMaxPoolSize(10);

        } catch (Exception e) {
            throw new RuntimeException("Error al configurar el pool de conexiones: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSources.getConnection();
    }

    private static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }
        return properties;
    }
}
