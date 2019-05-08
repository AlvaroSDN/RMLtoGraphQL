package com.code.RMLtoGraphQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {
	private static List<Resource> resources = new ArrayList<Resource>();
	private static RMLReader reader = new RMLReader();
	private static final File exampleDirectory = new File("C:\\Users\\Alvaro\\git\\RMLtoGraphQL\\RMLtoGraphQL\\Ejemplos");

	public static void main(String[] args) {
		//  String route = scanner.next();
		// File file = new File(route);
		//  resources = reader.read(file);
		// muestra el cuadro de diálogo de archivos, para que el usuario pueda elegir el archivo a abrir
		boolean ficheroValido = false;
		File mapping = null;
		JFileChooser selectorArchivos = new JFileChooser();
		selectorArchivos.setCurrentDirectory(exampleDirectory);
		selectorArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// indica cual fue la accion de usuario sobre el jfilechooser
		while(ficheroValido == false) {
			int result = selectorArchivos.showOpenDialog(selectorArchivos);
			mapping = selectorArchivos.getSelectedFile(); // obtiene el archivo seleccionado

			// muestra error si es inválido
			if ((mapping == null) || (mapping.getName().equals(""))) {
				JOptionPane.showMessageDialog(selectorArchivos, "Se ha seleccionado un archivo no valido, o ha pulsado en Cancelar.", "Error", JOptionPane.ERROR_MESSAGE);
				if(JOptionPane.showConfirmDialog(null, "¿Desea buscar otro archivo?", "RMLtoGraphQL", JOptionPane.YES_NO_OPTION) == 1) {
					return;
				}
			}
			else 
				ficheroValido = true;
		}
		resources = reader.read(mapping);
	}
}
