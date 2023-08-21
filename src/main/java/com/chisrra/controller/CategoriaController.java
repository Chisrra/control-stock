package com.chisrra.controller;

import com.chisrra.db.Categoria;
import com.chisrra.db.DAO.CategoriaDAO;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

	public List<Categoria> listar() {
        return CategoriaDAO.listar();
	}

    public List<Categoria> cargaReporte() {
        return CategoriaDAO.listar();
    }

}
