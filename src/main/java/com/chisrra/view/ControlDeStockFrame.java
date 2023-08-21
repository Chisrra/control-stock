package com.chisrra.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.chisrra.db.Categoria;
import com.chisrra.db.Producto;
import com.chisrra.controller.CategoriaController;
import com.chisrra.controller.ProductoController;

/**
 * Vista inicial de la aplicación que permite ver, registrar, eliminar y editar productos
 */
public class ControlDeStockFrame extends JFrame {

    // Componentes de la GUI
    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelDescripcion, labelCantidad, labelCategoria;
    private JTextField textoNombre, textoDescripcion, textoCantidad;
    private JComboBox<Object> comboCategoria;
    private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonReporte;
    private JTable tabla;
    private DefaultTableModel modelo;

    // Declaración de los controladores
    private ProductoController productoController;
    private CategoriaController categoriaController;

    /**
     * Constructor de la clase ControlDeStockFrame
     */
    public ControlDeStockFrame() {
        super("Productos");

        this.categoriaController = new CategoriaController();
        this.productoController = new ProductoController();

        Container container = getContentPane();
        setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);

        configurarAccionesDelFormulario();
    }

    /**
     * Método para configurar la tabla de contenido en el contenedor
     */
    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Identificador del Producto");
        modelo.addColumn("Nombre del Producto");
        modelo.addColumn("Descripción del Producto");
        modelo.addColumn("Cantidad");

        cargarTabla();

        tabla.setBounds(10, 205, 760, 280);

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        botonReporte = new JButton("Ver Reporte");
        botonEliminar.setBounds(10, 500, 80, 20);
        botonModificar.setBounds(100, 500, 80, 20);
        botonReporte.setBounds(190, 500, 80, 20);

        container.add(tabla);
        container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonReporte);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * Método para configurar los campos del formulario en el contenedor
     */
    private void configurarCamposDelFormulario(Container container) {
        labelNombre = new JLabel("Nombre del Producto");
        labelDescripcion = new JLabel("Descripción del Producto");
        labelCantidad = new JLabel("Cantidad");
        labelCategoria = new JLabel("Categoría del Producto");

        labelNombre.setBounds(10, 10, 240, 15);
        labelDescripcion.setBounds(10, 50, 240, 15);
        labelCantidad.setBounds(10, 90, 240, 15);
        labelCategoria.setBounds(10, 130, 240, 15);

        labelNombre.setForeground(Color.BLACK);
        labelDescripcion.setForeground(Color.BLACK);
        labelCategoria.setForeground(Color.BLACK);

        textoNombre = new JTextField();
        textoDescripcion = new JTextField();
        textoCantidad = new JTextField();
        comboCategoria = new JComboBox<>();
        comboCategoria.addItem("Elige una Categoría");

        List<Categoria> categorias = this.categoriaController.listar();
        categorias.forEach(categoria -> comboCategoria.addItem(categoria));

        textoNombre.setBounds(10, 25, 265, 20);
        textoDescripcion.setBounds(10, 65, 265, 20);
        textoCantidad.setBounds(10, 105, 265, 20);
        comboCategoria.setBounds(10, 145, 265, 20);

        botonGuardar = new JButton("Guardar");
        botonLimpiar = new JButton("Limpiar");
        botonGuardar.setBounds(10, 175, 80, 20);
        botonLimpiar.setBounds(100, 175, 80, 20);

        container.add(labelNombre);
        container.add(labelDescripcion);
        container.add(labelCantidad);
        container.add(labelCategoria);
        container.add(textoNombre);
        container.add(textoDescripcion);
        container.add(textoCantidad);
        container.add(comboCategoria);
        container.add(botonGuardar);
        container.add(botonLimpiar);
    }

    /**
     * Método para configurar las acciones de los botones del formulario
     */
    private void configurarAccionesDelFormulario() {
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardar();
                limpiarTabla();
                cargarTabla();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminar();
                limpiarTabla();
                cargarTabla();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificar();
                limpiarTabla();
                cargarTabla();
            }
        });

        botonReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirReporte();
            }
        });
    }

    /**
     * Abre la ventana de reporte para visualizar los productos.
     */
    private void abrirReporte() {
        new ReporteFrame(this);
    }

    /**
     * Limpia el contenido de la tabla de la GUI.
     */
    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    /**
     * Verifica si se ha seleccionado una fila en la tabla.
     * @return true si se ha seleccionado una fila, false si no.
     */
    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }

    /**
     * Modifica un producto seleccionado en la tabla.
     * Si no se ha seleccionado una fila, muestra un mensaje de error.
     * Si la fila está seleccionada, obtiene los valores y los utiliza para
     * actualizar el producto correspondiente en la base de datos.
     */
    private void modificar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
                    String nombre = (String) modelo.getValueAt(tabla.getSelectedRow(), 1);
                    String descripcion = (String) modelo.getValueAt(tabla.getSelectedRow(), 2);
                    Integer cantidad = Integer.valueOf((String) modelo.getValueAt(tabla.getSelectedRow(), 3));

                    this.productoController.modificar(new Producto(id, nombre, descripcion, cantidad));

                    JOptionPane.showMessageDialog(null, "Se actualizó el registro", "Actualización Exitosa",
                            JOptionPane.INFORMATION_MESSAGE);

                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }

    /**
     * Elimina un producto seleccionado en la tabla.
     * Si no se ha seleccionado una fila, muestra un mensaje de error.
     * Si la fila está seleccionada, obtiene el ID del producto y lo utiliza
     * para eliminar el producto correspondiente de la base de datos.
     */
    private void eliminar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());

                    int filasEliminadas = this.productoController.eliminar(id);
                    String prural = (filasEliminadas > 1) ? "items" : "item";
                    modelo.removeRow(tabla.getSelectedRow());

                    JOptionPane.showMessageDialog(this, String.format("Se han eliminado con éxito %d %s", filasEliminadas, prural));
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }

    /**
     * Carga los productos desde la base de datos y los muestra en la tabla.
     */
    private void cargarTabla() {
        var productos = this.productoController.listar();

        productos.forEach(producto -> modelo.addRow(
                new Object[] {
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getCantidad()
                }
        ));

    }

    /**
     * Guarda un nuevo producto en la base de datos con los valores ingresados en los campos de texto.
     * Realiza validaciones de campos requeridos y numéricos.
     */
    private void guardar() {
        if (textoNombre.getText().isBlank() || textoDescripcion.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Los campos Nombre y Descripción son requeridos.");
            return;
        }

        Integer cantidadInt;

        try {
            cantidadInt = Integer.parseInt(textoCantidad.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, String
                    .format("El campo cantidad debe ser numérico dentro del rango %d y %d.", 0, Integer.MAX_VALUE));
            return;
        }

        Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

        this.productoController.guardar(new Producto(textoNombre.getText(), textoDescripcion.getText(), cantidadInt, categoria));

        JOptionPane.showMessageDialog(this, "Registrado con éxito!");

        this.limpiarFormulario();
    }

    /**
     * Limpia el formulario, reseteando los campos de texto y combo box.
     */
    private void limpiarFormulario() {
        this.textoNombre.setText("");
        this.textoDescripcion.setText("");
        this.textoCantidad.setText("");
        this.comboCategoria.setSelectedIndex(0);
    }

}
