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

	public void generateCode(String routeCreate, RMLFile rmlFile) {
		String routeQueryClass = routeCreate + "\\Query.java";
		String routeMutationClass = routeCreate + "\\Mutation.java";
		String routeEndpointClass = routeCreate + "\\GraphQLEndpoint.java";
		File fileQueryClass = new File(routeQueryClass);
		File fileMutationClass = new File(routeMutationClass);
		File fileEndpointClass = new File(routeEndpointClass);

		generateQueryClass(fileQueryClass, rmlFile.getResources());
		generateMutationClass(fileMutationClass, rmlFile.getResources());
		generateEndpointClass(fileEndpointClass, rmlFile);
		generateResourcesCode(routeCreate, rmlFile.getResources());
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
	
	private void generateEndpointClass(File file, RMLFile rmlFile) {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(file));
			ST endpointTemplate = new ST(templates.getEndpointTemplate(rmlFile.getResources()));
			endpointTemplate.add("init", "<");
			endpointTemplate.add("end", ">");
			endpointTemplate.add("sourceName", rmlFile.getSource());
			for(int i = 1; i < rmlFile.getResources().size()+1; i++) {
				endpointTemplate.add("resourceName" + i, rmlFile.getResources().get(i-1).getNameClass());
				endpointTemplate.add("resourceVarName" + i, rmlFile.getResources().get(i-1).getNameClass().toLowerCase());
				endpointTemplate.add("iteratorName" + i, rmlFile.getResources().get(i-1).getIterator());
			}
			String endpointString = endpointTemplate.render();
			bw.write(endpointString);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateQueryClass(File file, List<Resource> resources) {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(file));
			ST queryTemplate = new ST(templates.getQueryTemplate(resources));
			queryTemplate.add("init", "<");
			queryTemplate.add("end", ">");
			for(int i = 1; i < resources.size()+1; i++) {
				queryTemplate.add("resourceName" + i, resources.get(i-1).getNameClass());
				queryTemplate.add("resourceVarName" + i, resources.get(i-1).getNameClass().toLowerCase());

			}
			String queryString = queryTemplate.render();
			bw.write(queryString);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateMutationClass(File file, List<Resource> resources) {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(file));
			ST mutationTemplate = new ST(templates.getMutationTemplate(resources));
			for(int i = 1; i < resources.size()+1; i++) {
				mutationTemplate.add("resourceName" + i, resources.get(i-1).getNameClass());
				mutationTemplate.add("resourceVarName" + i, resources.get(i-1).getNameClass().toLowerCase());
				for(int j = 1; j < resources.get(i-1).getPredicate().size()+1; j++) {
					mutationTemplate.add("predicateName" + i+j, resources.get(i-1).getPredicate().get(j-1).getPredicate());
					mutationTemplate.add("datatype" + i+j, resources.get(i-1).getPredicate().get(j-1).getObject().getDatatype());
				}
			}
			String mutationString = mutationTemplate.render();
			bw.write(mutationString);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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