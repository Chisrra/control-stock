package com.chisrra.db;

/**
 * Representa un producto del sistema control stock
 */
public class Producto {
    private int id;
    private final String nombre;
    private final String descripcion;
    private final int cantidad;
    private Categoria fk_categoria;


    /**
     * Constructor para un producto temporal con identificador.
     * @param id El identificador único del producto.
     * @param nombre El nombre del producto.
     * @param descripcion La descripción del producto.
     * @param cantidad La cantidad disponible del producto.
     */
    public Producto(int id, String nombre, String descripcion, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    /**
     * Constructor para un producto con información completa.
     * @param nombre El nombre del producto.
     * @param descripcion La descripción del producto.
     * @param cantidad La cantidad disponible del producto.
     * @param fk_categoria La categoría a la que pertenece el producto.
     */
    public Producto(String nombre, String descripcion, int cantidad, Categoria fk_categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fk_categoria = fk_categoria;
    }

    /**
     * Obtiene el identificador del producto
     * @return El identificador único del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la descripción del producto.
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la cantidad disponible del producto.
     * @return La cantidad disponible del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene la categoría a la que pertenece el producto.
     * @return La categoría a la que pertenece el producto.
     */
    public Categoria getFk_categoria() {
        return fk_categoria;
    }

    /**
     * Establece el identificador del producto.
     * @param id El identificador único del producto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la categoría a la que pertenece el producto.
     * @param fk_categoria La categoría a la que pertenece el producto.
     */
    public void setFk_categoria(Categoria fk_categoria) {
        this.fk_categoria = fk_categoria;
    }

    /**
     * Devuelve una representación en formato de cadena del objeto Producto.
     * @return Una cadena que muestra el identificador, nombre, descripción, cantidad y categoría del producto.
     */
    @Override
    public String toString() {
        return String.format("{ID: %d, NOMBRE: %s, DESCRIPCION: %s, CANTIDAD: %d, CATEGORIA: %s}", id, nombre, descripcion, cantidad, fk_categoria);
    }



}
