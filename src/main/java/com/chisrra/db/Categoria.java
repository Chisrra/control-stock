package com.chisrra.db;

import java.util.List;

/**
 * Clase que representa una categoría de productos
 */
public class Categoria {
    private int id;
    private String nombre;
    private List<Producto> productos;

    /**
     * Constructor de la clase Categoria.
     * Crea una nueva categoría con el ID y el nombre especificados.
     * @param id El identificador único de la categoría.
     * @param nombre El nombre de la categoría.
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Constructor de la clase Categoria.
     * Crea una nueva categoría con el ID, el nombre y la lista de productos especificados.
     * @param id El identificador único de la categoría.
     * @param nombre El nombre de la categoría.
     * @param productos La lista de productos asociados a esta categoría.
     */
    public Categoria(int id, String nombre, List<Producto> productos) {
        this.id = id;
        this.nombre = nombre;
        this.productos = productos;
    }

    /**
     * Obtiene el ID de la categoría.
     * @return El ID de la categoría.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la categoría.
     * @param id El ID de la categoría a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la categoría.
     * @return El nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     * @param nombre El nombre de la categoría a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de productos asociados a esta categoría.
     * @return La lista de productos asociados a esta categoría.
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Establece la lista de productos asociados a esta categoría.
     * @param productos La lista de productos a establecer.
     */
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    /**
     * Agrega un producto a la lista de productos asociados a esta categoría.
     * @param producto El producto a agregar.
     */
    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    /**
     * Devuelve una representación en cadena de la categoría (su nombre).
     * @return El nombre de la categoría.
     */
    @Override
    public String toString() {
        return this.nombre;
    }


}
