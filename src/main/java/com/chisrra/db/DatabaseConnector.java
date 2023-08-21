package com.chisrra.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase utilitaria que proporciona métodos para establecer y obtener conexiones de la base de datos mediante un pool de conexiones.
 */
public class DatabaseConnector {
    private static ComboPooledDataSource dataSources;

    /**
     * Inicializa el pool de conexiones utilizando las propiedades del archivo de configuración.
     */
    static {
        try {
            dataSources = new ComboPooledDataSource();
            Properties properties = loadProperties("src/config.properties");
            dataSources.setJdbcUrl(properties.getProperty("db.url"));
            dataSources.setUser(properties.getProperty("db.username"));
            dataSources.setPassword(properties.getProperty("db.password"));

            dataSources.setMaxPoolSize(10);

        } catch (Exception e) {
            throw new RuntimeException("Error al configurar el pool de conexiones: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una conexión de la base de datos desde el pool de conexiones.
     * @return Una instancia de la clase Connection que representa una conexión a la base de datos.
     * @throws SQLException Si ocurre un error al obtener la conexión.
     */
    public static Connection getConnection() throws SQLException {
        return dataSources.getConnection();
    }

    /**
     * Carga las propiedades desde un archivo y las devuelve como un objeto Properties.
     * @param filePath La ruta del archivo de propiedades a cargar.
     * @return Un objeto Properties que contiene las propiedades cargadas desde el archivo.
     * @throws IOException Si ocurre un error al cargar el archivo de propiedades.
     */
    private static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }
        return properties;
    }
}
