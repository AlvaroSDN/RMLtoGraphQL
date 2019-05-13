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
		generateResourcesClass(routeCreate, resources);
	}

	private void generateResourcesClass(String routeCreate, List<Resource> resources) {
		for(int i = 0; i < resources.size(); i++) {
			String routeFile = routeCreate + "\\" + resources.get(i).getNameClass() + ".java";
			File file = new File(routeFile);
			generateResourceClass(file, resources.get(i));
		}
	}


	private void generateResourceClass(File file, Resource resource) {
		
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(file));
			ST fileClassTemplate = new ST(templates.getResourceClassTemplate(resource));
			fileClassTemplate.add("resourceName", resource.getNameClass());
			for(int i = 1; i < resource.getPredicate().size()+1; i++) {
				fileClassTemplate.add("predicateName" + i, resource.getPredicate().get(i-1).getPredicate());
				fileClassTemplate.add("datatype" + i, resource.getPredicate().get(i-1).getObject().getDatatype());
			}
			String fileClassString = fileClassTemplate.render();
			bw.write(fileClassString);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}