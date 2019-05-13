package com.code.RMLtoGraphQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.stringtemplate.v4.ST;

public class CodeGenerator  {
	private Templates templates;

	public CodeGenerator(Templates templates) {
		this.templates = templates;
	}	

	public void generateCode(String routeCreate, List<Resource> resources) {
		routeCreate += "\\Student.java";
		File schema = new File(routeCreate);
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(schema));
			ST schemaTemplate = new ST(templates.getResourceClassTemplate(resources.get(0)));
				schemaTemplate.add("resourceName", resources.get(0).getNameClass());
				for(int i = 1; i < resources.get(0).getPredicate().size()+1; i++) {
					schemaTemplate.add("predicateName" + i, resources.get(0).getPredicate().get(i-1).getPredicate());
					schemaTemplate.add("datatype" + i, resources.get(0).getPredicate().get(i-1).getObject().getDatatype());
			}
			String schemaString = schemaTemplate.render();
			bw.write(schemaString);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void generateResourceClass(Resource resource) {
		
	}
}