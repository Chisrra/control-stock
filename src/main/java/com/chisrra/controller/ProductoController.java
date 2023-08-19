package com.chisrra.controller;

import com.chisrra.db.DatabaseConnector;
import com.chisrra.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(Producto producto) {
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

	public int eliminar(Integer id) {
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

	public List<Map<String, String>> listar() {
		List<Map<String, String>> consulta = new ArrayList<>();

		try (Connection connection = DatabaseConnector.getConnection()) {
			String query = "SELECT id, nombre, descripcion, cantidad FROM producto";

			try(Statement statement = connection.createStatement()) {
				statement.execute(query);
				ResultSet resultSet = statement.getResultSet();

				while(resultSet.next()) {
					Map<String, String> registro = new HashMap<>();
					registro.put("ID", String.valueOf(resultSet.getInt("id")));
					registro.put("NOMBRE", resultSet.getString("nombre"));
					registro.put("DESCRIPCION", resultSet.getString("descripcion"));
					registro.put("CANTIDAD", resultSet.getString("cantidad"));

					consulta.add(registro);
				}
			}

		} catch (SQLException e) {
			System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
		}

		return consulta;
	}

    public void guardar(Producto producto) {
		try(Connection connection = DatabaseConnector.getConnection()) {
			connection.setAutoCommit(false);


			int cantidad = producto.getCantidad();
			int cantidadMax = 50;

			try {
				registratProducto( connection, producto);
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException("Error al guardar el producto en la base de datos: " + e.getMessage(), e);
			}

			connection.commit();

		} catch (SQLException e) {
			throw new RuntimeException("Error al crear la conexión: " + e.getMessage(), e);
		}
	}

	private static void registratProducto(Connection connection, Producto producto) throws SQLException {
		String insertQuery = "INSERT INTO producto (nombre, descripcion, cantidad) VALUES (?, ?, ?)";

		try(PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, producto.getNombre());
			preparedStatement.setString(2, producto.getDescripcion());
			preparedStatement.setInt(3, producto.getCantidad());

			int rowsAffected = preparedStatement.executeUpdate();
			if(rowsAffected > 0) {
				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					while(resultSet.next()) {
						producto.setId(resultSet.getInt(1));
						System.out.println("Se agrego el producto:" + producto);
					}
				}
			} else {
				System.err.println("No se pudo insertar el procuto");
			}
		}
	}

}
