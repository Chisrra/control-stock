package com.chisrra.controller;

import com.chisrra.db.Categoria;
import com.chisrra.db.Producto;
import com.chisrra.db.DAO.ProductoDAO;

import java.util.List;

/**
 * Controlador para la entidad Producto que realiza operaciones relacionadas con la base de datos.
 */
public class ProductoController {

	/**
	 * Modifica un producto existente en la base de datos.
	 *
	 * @param producto Producto a modificar.
	 * @return Número de filas afectadas por la modificación.
	 */
	public int modificar(Producto producto) {
		return ProductoDAO.modificar(producto);
	}

	/**
	 * Elimina un producto de la base de datos.
	 *
	 * @param id Identificador del producto a eliminar.
	 * @return Número de filas afectadas por la eliminación.
	 */
	public int eliminar(Integer id) {
		return ProductoDAO.eliminar(id);
	}

	/**
	 * Obtiene una lista de todos los productos.
	 *
	 * @return Lista de productos.
	 */
	public List<Producto> listar() {
		return ProductoDAO.listar();
	}

	/**
	 * Obtiene una lista de productos asociados a una categoría específica.
	 *
	 * @param categoria Categoría para la cual se desean listar los productos.
	 * @return Lista de productos asociados a la categoría.
	 */
	public List<Producto> listar(Categoria categoria) {
		return ProductoDAO.listar(categoria.getId());
	}

	/**
	 * Guarda un nuevo producto en la base de datos.
	 *
	 * @param producto Producto a guardar.
	 */
	public void guardar(Producto producto) {
		if (ProductoDAO.guardarProducto(producto)) {
			System.out.println("Se agregó el producto: " + producto);
		}
	}
}

