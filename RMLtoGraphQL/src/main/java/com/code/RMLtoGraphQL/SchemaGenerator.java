package com.code.RMLtoGraphQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.stringtemplate.v4.ST;

public class SchemaGenerator {
	private Templates templates;

	public SchemaGenerator(Templates templates) {
		this.templates = templates;
	}	

	public void generateSchema(String routeCreate, List<Resource> resources) {
		routeCreate += "\\schema.graphqls";
		File schema = new File(routeCreate);
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(schema));
			ST schemaTemplate = new ST(templates.getSchemaTemplate(resources.size()));
			for(int i = 1; i < resources.size()+1; i++) {
				schemaTemplate.add("resourceName" + i, resources.get(i-1).getNameClass());
			}
			String schemaString = schemaTemplate.render();
			bw.write(schemaString);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
