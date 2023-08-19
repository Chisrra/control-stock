package com.chisrra.controller;

import com.chisrra.db.DatabaseConnector;
import com.chisrra.Producto;
import com.chisrra.db.ProductoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(Producto producto) {
		return ProductoDAO.modificar(producto);
	}

	public int eliminar(Integer id) {
		return ProductoDAO.eliminar(id);
	}

	public List<Producto> listar() {
		return ProductoDAO.listar();
	}

    public void guardar(Producto producto) {
		if( ProductoDAO.guardarProcuto(producto) ) System.out.println("Se agrego el producto:" + producto);
	}



}
