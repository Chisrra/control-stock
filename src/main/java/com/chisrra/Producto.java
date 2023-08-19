package com.chisrra;

public class Producto {
    private int id;
    private final String nombre;
    private final String descripcion;
    private final int cantidad;

    public Producto(int id, String nombre, String descripcion, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public Producto(String nombre, String descripcion, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
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

    public void setId(int id) {
       this.id = id;
    }

    @Override
    public String toString() {
        return String.format("{ID: %d, NOMBRE: %s, DESCRIPCION: %s, CANTIDAD: %d}", id, nombre, descripcion, cantidad);
    }
}
