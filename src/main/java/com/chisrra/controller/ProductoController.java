package com.chisrra.controller;

import com.chisrra.test.DatabaseConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		try(Connection connection = DatabaseConnector.createConnection()) {
			String updateQuery = "UPDATE producto SET nombre = ?, descripcion = ? , cantidad = ? WHERE id = ?;";

			try(PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, nombre);
				preparedStatement.setString(2, descripcion);
				preparedStatement.setInt(3, cantidad);
				preparedStatement.setInt(4, id);

				int rowsAffected = preparedStatement.executeUpdate();

				return rowsAffected;

			}

		} catch (SQLException e) {
			throw new RuntimeException("Error al intentar modificar a la base de datos: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar el archivo de propiedades: " +  e.getMessage(), e);
		}
	}

	public int eliminar(Integer id) {
		try(Connection connection = DatabaseConnector.createConnection()) {
			String deleteQuery = "DELETE FROM producto WHERE id = ?";

			try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
				preparedStatement.setInt(1, id);

				int rowsAffected = preparedStatement.executeUpdate();
				return rowsAffected;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al intentar eliminar el producto de la base de datos: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar el archivo de propiedades: " + e.getMessage(), e);
		}

	}

	public List<Map<String, String>> listar() {
		List<Map<String, String>> consulta = new ArrayList<>();

		try (Connection connection = DatabaseConnector.createConnection()) {
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
		} catch (IOException e) {
			System.err.println("Error al cargar el archivo de propiedades: " + e.getMessage());
		}

		return consulta;
	}

    public void guardar(HashMap<String, String> producto) {
		try(Connection connection = DatabaseConnector.createConnection()) {
			connection.setAutoCommit(false);

			String nombre = producto.get("NOMBRE");
			String descripcion = producto.get("DESCRIPCION");
			int cantidad = Integer.valueOf(producto.get("CANTIDAD"));
			int cantidadMax = 50;

			try {
				do {
					registratProducto( connection, nombre, descripcion, Math.min(cantidadMax, cantidad) );
					cantidad -= cantidadMax;
				}while(cantidad > 0);
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException("Error al guardar el producto en la base de datos: " + e.getMessage(), e);
			}

			connection.commit();

		} catch (SQLException e) {
			throw new RuntimeException("Error al crear la conexión: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar el archivo de propiedades: " + e.getMessage(), e);
		}
	}

	private static void registratProducto(Connection connection, String nombre, String descripcion, int cantidad) throws SQLException {
		String insertQuery = "INSERT INTO producto (nombre, descripcion, cantidad) VALUES (?, ?, ?)";

		try(PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, descripcion);
			preparedStatement.setInt(3, cantidad);

			int rowsAffected = preparedStatement.executeUpdate();
			if(rowsAffected > 0) {
				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					while(resultSet.next()) {
						System.out.printf("ID insertado: %d", resultSet.getInt(1));
					}
				}
			} else {
				System.err.println("No se pudo insertar el procuto");
			}
		}
	}

}
