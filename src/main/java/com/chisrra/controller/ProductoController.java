package com.chisrra.controller;

import com.chisrra.db.Categoria;
import com.chisrra.db.Producto;
import com.chisrra.db.DAO.ProductoDAO;

import java.util.List;

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

	public List<Producto> listar(Categoria categoria) { return ProductoDAO.listar( categoria.getId() ); }

    public void guardar(Producto producto) {
		if( ProductoDAO.guardarProcuto(producto) ) System.out.println("Se agrego el producto:" + producto);
	}



}
