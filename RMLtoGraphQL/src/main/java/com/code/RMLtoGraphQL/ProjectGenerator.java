package com.code.RMLtoGraphQL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ProjectGenerator {
	private static String routeCreate;
	private static String routeServer;
	private static List<Resource> resources;
	private static Templates templates;
	private static SchemaGenerator schemaGenerator;
	private static CodeGenerator codeGenerator;

	public ProjectGenerator(String routeCreate, List<Resource> resources, Templates templates) {
		this.routeCreate = routeCreate + "\\ServidorGraphQL";
		this.resources = resources;
		this.templates = templates;
		
		routeServer = new File("").getAbsolutePath() + "\\serverData";
		
		schemaGenerator = new SchemaGenerator(templates);
		codeGenerator = new CodeGenerator(templates);
	}

	public void generateProject() {
		copyDirectory(new File(routeServer), new File(routeCreate));
		
		String routeSchema = routeCreate + "\\src\\main\\resources";
		schemaGenerator.generateSchema(routeSchema, resources);
		
		String routeCode = routeCreate + "\\src\\main\\java\\com\\servidorGraphQL\\code";
		codeGenerator.generateCode(routeCode, resources);
	}

	private static void copyDirectory(File d1, File d2) {
		if(d1.isDirectory()) {
			if (!d2.exists()){                              
				d2.mkdir();
			}
			String[] files = d1.list();
			for (int i = 0; i < files.length; i++) {
			  copyDirectory(new File(d1, files[i]), new File(d2, files[i]));                           
			}
		}
		else {
			copyFile(d1, d2);
		}
	}

	private static void copyFile(File f1, File f2) {
		try {
			InputStream in = new FileInputStream(f1);
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			in.close();
			out.close();

		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
}
