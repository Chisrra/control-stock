package com.chisrra.db.DAO;

import com.chisrra.db.Categoria;
import com.chisrra.db.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CategoriaDAO {
    static List<Categoria> listar() {
        List<Categoria> categoriaList = new ArrayList<>();

        try( Connection connection = DatabaseConnector.getConnection() ) {
            String querySelect = "SELECT * FROM categoria;";
            System.out.println(querySelect);
            try( PreparedStatement preparedStatement = connection.prepareStatement(querySelect) ) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while( resultSet.next() ) {
                    Categoria categoria = new Categoria( resultSet.getInt("id"), resultSet.getString("nombre") );
                    categoriaList.add( categoria );
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la conexión: "+ e.getMessage(), e);
        }
        return categoriaList;
    }
}
