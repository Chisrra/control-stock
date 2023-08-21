package com.chisrra;

import javax.swing.JFrame;

import com.chisrra.view.ControlDeStockFrame;

/**
 * Clase que ejecuta la aplicación  de control stock utilizando las librerias de swing
 * @version 1.0
 */
public class ControlDeStockMain {

	/**
	 * Método principal para la ejecución de la aplicación control stock
	 * @param args
	 */
	public static void main(String[] args) {
		ControlDeStockFrame produtoCategoriaFrame = new ControlDeStockFrame();
		produtoCategoriaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
