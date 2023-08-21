package com.chisrra.controller;

import com.chisrra.db.Categoria;
import com.chisrra.db.DAO.CategoriaDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la entidad Categoria que realiza operaciones relacionadas con la base de datos.
 */
public class CategoriaController {

    /**
     * Obtiene una lista de todas las categorías de productos.
     *
     * @return Lista de categorías.
     */
    public List<Categoria> listar() {
        return CategoriaDAO.listar();
    }

    /**
     * Obtiene una lista de categorías junto con información detallada de los productos asociados a cada categoría.
     *
     * @return Lista de categorías con información de productos.
     */
    public List<Categoria> cargaReporte() {
        return CategoriaDAO.categoriaProductoView();
    }
}

