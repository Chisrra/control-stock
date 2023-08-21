package com.chisrra.db;

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private Categoria fk_categoria;

    //temp
    public Producto(int id, String nombre, String descripcion, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public Producto(String nombre, String descripcion, int cantidad, Categoria fk_categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fk_categoria = fk_categoria;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Categoria getFk_categoria() {
        return fk_categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFk_categoria(Categoria fk_categoria) { this.fk_categoria = fk_categoria; }

    @Override
    public String toString() {
        return String.format("{ID: %d, NOMBRE: %s, DESCRIPCION: %s, CANTIDAD: %d, CATEGORIA: %s}", id, nombre, descripcion, cantidad, fk_categoria);
    }


}
