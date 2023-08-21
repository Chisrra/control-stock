package com.chisrra.db.DAO;

import com.chisrra.db.Categoria;
import com.chisrra.db.DatabaseConnector;
import com.chisrra.db.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz que define métodos para acceder y manipular datos de la tabla de categorías en la base de datos.
 */
public interface CategoriaDAO {

    /**
     * Obtiene una lista de todas las categorías existentes en la base de datos.
     * @return Lista de objetos Categoria representando las categorías almacenadas.
     * @throws RuntimeException Si ocurre un error al obtener las categorías.
     */
    static List<Categoria> listar() {
        List<Categoria> categoriaList = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String querySelect = "SELECT * FROM categoria;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(querySelect)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Categoria categoria = new Categoria(resultSet.getInt("id"), resultSet.getString("nombre"));
                    categoriaList.add(categoria);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la conexión: " + e.getMessage(), e);
        }
        return categoriaList;
    }

    /**
     * Obtiene una lista de categorías junto con los productos asociados a cada categoría.
     * @return Lista de objetos Categoria con productos asociados.
     * @throws RuntimeException Si ocurre un error al obtener las categorías y sus productos.
     */
    static List<Categoria> categoriaProductoView() {
        List<Categoria> categoriaList = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String querySelect = "SELECT C.id, C.nombre, " +
                    "GROUP_CONCAT(P.id) AS p_ids, " +
                    "GROUP_CONCAT(P.nombre) AS p_nombres, " +
                    "GROUP_CONCAT(P.cantidad) AS p_cantidades " +
                    "FROM categoria AS C INNER JOIN producto AS P ON C.id = P.fk_categoria_id " +
                    "GROUP BY C.id, C.nombre";
            System.out.println(querySelect);

            try (PreparedStatement preparedStatement = connection.prepareStatement(querySelect)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int cId = resultSet.getInt("C.id");
                    String cNombre = resultSet.getString("C.nombre");
                    String[] productosIds = resultSet.getString("p_ids").split(",");
                    String[] productosNombres = resultSet.getString("p_nombres").split(",");
                    String[] productosCantidades = resultSet.getString("p_cantidades").split(",");

                    List<Producto> productos = new ArrayList<>();
                    for (int i = 0; i < productosIds.length; i++) {
                        productos.add(new Producto(
                                Integer.parseInt(productosIds[i]),
                                productosNombres[i],
                                "",
                                Integer.parseInt(productosCantidades[i])));
                    }

                    categoriaList.add(new Categoria(cId, cNombre, productos));

                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la conexión: " + e.getMessage(), e);
        }
        return categoriaList;
    }
}

