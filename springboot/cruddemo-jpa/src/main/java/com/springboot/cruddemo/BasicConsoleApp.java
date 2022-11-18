package com.springboot.cruddemo;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BasicConsoleApp {
	public static final String FILE_EXTENSION_NAME = "SQL FILES";
	public static final String FILE_EXTENSION = "sql";
	
	static {
		System.out.println("First static block");
	}
	
	public static void main(String[] args) throws IOException {
//		JFileChooser jfc = new JFileChooser(new File("C:\\"));
//		jfc.setDialogTitle("Choose a directory to save your file: ");
//		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//		FileNameExtensionFilter filter = new FileNameExtensionFilter(FILE_EXTENSION_NAME, FILE_EXTENSION);
//		jfc.setFileFilter(filter);
//
//		int returnValue = jfc.showSaveDialog(null);
//		if (returnValue == JFileChooser.APPROVE_OPTION) {
//			if (jfc.getSelectedFile().isDirectory()) {
//				System.out.println("You selected the directory: " + jfc.getSelectedFile());
//			}
//		}
		
		System.out.println("Inside main");
	}
	
	static {
		System.out.println("Second static block");
	}
	
}
