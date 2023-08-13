package com.chisrra.controller;

import com.chisrra.test.DatabaseConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
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
			System.out.println("Conexión exitosa");
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
			String insertQuery = "INSERT INTO producto (nombre, descripcion, cantidad) VALUES (?, ?, ?)";

			try(PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, producto.get("NOMBRE"));
				preparedStatement.setString(2, producto.get("DESCRIPCION"));
				preparedStatement.setString(3, producto.get("CANTIDAD"));

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

		} catch (SQLException e) {
			throw new RuntimeException("Error al guardar el producto en la base de datos: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar el archivo de propiedades: " + e.getMessage(), e);
		}
	}

}
