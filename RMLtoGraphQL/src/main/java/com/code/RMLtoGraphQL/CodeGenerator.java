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
		generateResourcesCode(routeCreate, resources);
	}



	private void generateResourcesCode(String routeCreate, List<Resource> resources) {
		for(int i = 0; i < resources.size(); i++) {
			String routeFileClass = routeCreate + "\\" + resources.get(i).getNameClass() + ".java";
			String routeFileRepository = routeCreate + "\\" + resources.get(i).getNameClass() + "Repository.java";
			File fileClass = new File(routeFileClass);
			File fileRepository = new File(routeFileRepository);
			generateResourceClass(fileClass, resources.get(i));
			generateResourceRepository(fileRepository, resources.get(i));
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
				fileClassTemplate.add("predicateGetterName" + i, Character.toUpperCase(resource.getPredicate().get(i-1).getPredicate().charAt(0)) + 
						resource.getPredicate().get(i-1).getPredicate().substring(1,resource.getPredicate().get(i-1).getPredicate().length()));
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

	private void generateResourceRepository(File file, Resource resource) {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(file));
			ST fileRepositoryTemplate = new ST(templates.getResourceRepositoryTemplate(resource));
			fileRepositoryTemplate.add("resourceName", resource.getNameClass());
			fileRepositoryTemplate.add("resourceVarName", resource.getNameClass().toLowerCase());
			fileRepositoryTemplate.add("init", "<");
			fileRepositoryTemplate.add("end", ">");
			for(int i = 1; i < resource.getPredicate().size()+1; i++) {
				fileRepositoryTemplate.add("referenceName" + i, resource.getPredicate().get(i-1).getObject().getReference());
				fileRepositoryTemplate.add("datatypeGetterName" + i, Character.toUpperCase(resource.getPredicate().get(i-1).getObject().getDatatype().charAt(0)) + 
						resource.getPredicate().get(i-1).getObject().getDatatype().substring(1,resource.getPredicate().get(i-1).getObject().getDatatype().length()));
				fileRepositoryTemplate.add("predicateGetterName" + i, Character.toUpperCase(resource.getPredicate().get(i-1).getPredicate().charAt(0)) + 
						resource.getPredicate().get(i-1).getPredicate().substring(1,resource.getPredicate().get(i-1).getPredicate().length()));
			} 
			String fileRepositoryString = fileRepositoryTemplate.render();
			bw.write(fileRepositoryString);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}