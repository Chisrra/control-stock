package com.chisrra.db.DAO;

import com.chisrra.db.Producto;
import com.chisrra.db.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz que define las operaciones relacionadas con la base de datos para la entidad Producto.
 */
public interface ProductoDAO {

    /**
     * Guarda un producto en la base de datos.
     *
     * @param producto El producto a guardar.
     * @return true si el producto se guardó exitosamente, false en caso contrario.
     * @throws RuntimeException Si ocurre un error durante la operación.
     */
    static boolean guardarProducto(Producto producto) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            try {
                registrarProducto(connection, producto);
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

    private static void registrarProducto(Connection connection, Producto producto) throws SQLException {
        String insertQuery = "INSERT INTO producto (nombre, descripcion, cantidad, fk_categoria_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setInt(3, producto.getCantidad());
            preparedStatement.setInt(4, producto.getFk_categoria().getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        producto.setId(resultSet.getInt(1));
                    }
                }
            } else {
                System.err.println("No se pudo insertar el producto");
            }
        }
    }

    /**
     * Obtiene una lista de todos los productos en la base de datos.
     *
     * @return Lista de productos.
     * @throws RuntimeException Si ocurre un error durante la operación.
     */
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

    /**
     * Obtiene una lista de productos filtrados por el ID de una categoría.
     *
     * @param id ID de la categoría.
     * @return Lista de productos pertenecientes a la categoría.
     * @throws RuntimeException Si ocurre un error durante la operación.
     */
    static List<Producto> listar(int id) {
        List<Producto> consulta = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT id, nombre, descripcion, cantidad FROM producto WHERE fk_categoria_id = ?";
            System.out.println(query);

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

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

    /**
     * Elimina un producto de la base de datos por su ID.
     *
     * @param id ID del producto a eliminar.
     * @return Número de filas afectadas por la eliminación.
     * @throws RuntimeException Si ocurre un error durante la operación.
     */
    static int eliminar(int id) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String deleteQuery = "DELETE FROM producto WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar eliminar el producto de la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Modifica un producto en la base de datos.
     *
     * @param producto Producto modificado.
     * @return Número de filas afectadas por la modificación.
     * @throws RuntimeException Si ocurre un error durante la operación.
     */
    static int modificar(Producto producto) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String updateQuery = "UPDATE producto SET nombre = ?, descripcion = ?, cantidad = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, producto.getNombre());
                preparedStatement.setString(2, producto.getDescripcion());
                preparedStatement.setInt(3, producto.getCantidad());
                preparedStatement.setInt(4, producto.getId());

                return preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar modificar la base de datos: " + e.getMessage(), e);
        }
    }
}
