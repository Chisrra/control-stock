package com.chisrra.view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.chisrra.controller.CategoriaController;
import com.chisrra.controller.ProductoController;

/**
 * Clase que representa la ventana de reporte de productos del stock.
 */
public class ReporteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private DefaultTableModel modelo;

    private CategoriaController categoriaController;
    private ProductoController productoController;

    /**
     * Constructor de la clase ReporteFrame.
     * Crea una nueva ventana de reporte de productos del stock.
     * @param controlDeStockFrame La ventana de ControlDeStockFrame desde la cual se abre el reporte.
     */
    public ReporteFrame(ControlDeStockFrame controlDeStockFrame) {
        super("Reporte de produtos del stock");

        this.categoriaController = new CategoriaController();
        this.productoController = new ProductoController();

        Container container = getContentPane();
        setLayout(null);

        tablaReporte = new JTable();
        tablaReporte.setBounds(0, 0, 600, 400);
        container.add(tablaReporte);

        modelo = (DefaultTableModel) tablaReporte.getModel();
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");

        cargaReporte();

        setSize(600, 400);
        setVisible(true);
        setLocationRelativeTo(controlDeStockFrame);
    }

    /**
     * Carga y muestra el reporte en la tabla.
     * Obtiene la información del reporte desde el controlador de categorías.
     * Llena la tabla con las categorías y sus productos correspondientes.
     */
    private void cargaReporte() {
        var contenido = categoriaController.cargaReporte();

        contenido.forEach(categoria -> {
            modelo.addRow(new Object[]{categoria});
            var productos = categoria.getProductos();

            productos.forEach(producto -> modelo.addRow(
                    new Object[]{
                    "",
                    producto.getNombre(),
                    producto.getCantidad()
            }));
        });
    }

}
