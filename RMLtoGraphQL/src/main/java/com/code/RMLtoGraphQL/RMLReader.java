package com.code.RMLtoGraphQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resources;

public class RMLReader {
	private static final String triplesMap = "rr:TriplesMap";
	private static final String nameClass = "rr:class ";
	private static final String predicateObjectMap = "rr:predicateObjectMap";
	private static final String predicate = "rr:predicate ";
	private static final String objectMap = "rr:objectMap";
	private static final String reference = "rml:reference ";
	private static final String datatype = "rr:datatype ";

	public RMLReader() {
		super();
	}

	List<Resource> read(File mapping) {
		List<Resource> resources = new ArrayList<Resource>();
		FileReader fr = null;
		try {
			fr = new FileReader(mapping);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		List<Predicate> predicates = new ArrayList<Predicate>();
		String line, nameClassString = null, predicateString = null, referenceString = null, datatypeString = null;
		Resource resource;
		try {
			while((line = br.readLine()) != null) {
				if(line.contains(triplesMap)) {
					while(!(line = br.readLine()).contains("].")) {
						if(line.contains(nameClass)) {
							line = getParameter(line, nameClass);
							nameClassString = line;
						}
						else if(line.contains(predicate)) {
							line = getParameter(line, predicate);
							predicateString = line;
							predicates.add(new Predicate(predicateString, new ObjectMap()));
						}
						else if(line.contains(reference)) {
							line = getParameter(line, reference);
							referenceString = line;
							predicates.get(predicates.size()-1).getObject().setReference(referenceString);
						}
						else if(line.contains(datatype)) {
							line = getParameter(line, datatype);
							datatypeString = line;
							predicates.get(predicates.size()-1).getObject().setDatatype(datatype);
						}
					}
					resource = new Resource(nameClassString, predicates);
					resources.add(resource);
					predicates = new ArrayList<Predicate>();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		seeResources(resources);
		return resources;
	} 
	
	String getParameter(String line, String typeParameter) {
		line = line.trim();
		line = line.replace("\t", "");
		int index = line.indexOf(typeParameter);
		line = line.substring(index);
		index = line.indexOf(" ") + 1;
		line = line.substring(index);
		if(line.charAt(line.length()-1) == ';' || line.charAt(line.length()-1) == ','
				|| line.charAt(line.length()-1) == ']') {
			line = line.substring(0, line.length()-1);
		}
		if(line.charAt(0) == '"') {
			line = line.substring(1, line.length()-1);
		}
		return line;
	}
	
	void seeResources(List<Resource> resources) {
		for(int i = 0; i < resources.size(); i++) {
			System.out.println(resources.get(i));
		}
	}
}
