package com.chisrra.db;

import com.chisrra.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface ProductoDAO {

    static boolean guardarProcuto(Producto producto) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            try {
                registratProducto(connection, producto);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Error al guardar el producto en la base de datos: " + e.getMessage(), e);
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la conexión: " + e.getMessage(), e);
        }
    }

    private static void registratProducto(Connection connection, Producto producto) throws SQLException {
        String insertQuery = "INSERT INTO producto (nombre, descripcion, cantidad) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setInt(3, producto.getCantidad());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        producto.setId(resultSet.getInt(1));
                    }
                }
            } else {
                System.err.println("No se pudo insertar el procuto");
            }
        }
    }

    static List<Producto> listar() {
        List<Producto> consulta = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT id, nombre, descripcion, cantidad FROM producto";

            try (Statement statement = connection.createStatement()) {
                statement.execute(query);
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    Producto producto = new Producto(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("descripcion"),
                            resultSet.getInt("cantidad"));
                    consulta.add(producto);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
        }

        return consulta;
    }

    static int eliminar(int id) {
        try(Connection connection = DatabaseConnector.getConnection()) {
            String deleteQuery = "DELETE FROM producto WHERE id = ?";

            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar eliminar el producto de la base de datos: " + e.getMessage(), e);
        }
    }

    static int modificar(Producto producto) {
        try(Connection connection = DatabaseConnector.getConnection()) {
            String updateQuery = "UPDATE producto SET nombre = ?, descripcion = ? , cantidad = ? WHERE id = ?;";

            try(PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, producto.getNombre());
                preparedStatement.setString(2, producto.getDescripcion());
                preparedStatement.setInt(3, producto.getCantidad());
                preparedStatement.setInt(4, producto.getId());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected;

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar modificar a la base de datos: " + e.getMessage(), e);
        }
    }
}
