package com.code.RMLtoGraphQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RMLReader {
	private static final String triplesMap = "rr:TriplesMap";
	private static final String nameClass = "rr:class ";
	private static final String predicateObjectMap = "rr:predicateObjectMap";
	private static final String predicate = "rr:predicate ";
	private static final String objectMap = "rr:objectMap";
	private static final String reference = "rml:reference ";
	private static final String datatype = "rr:datatype ";

	public RMLReader() {}

	List<Resource> read(File mapping) {
		List<Resource> resources = new ArrayList<Resource>();
		FileReader fr = null;
		try {
			fr = new FileReader(mapping);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
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
						}
						else if(line.contains(reference)) {
							line = getParameter(line, reference);
							referenceString = line;
						}
						else if(line.contains(datatype)) {
							line = getParameter(line, datatype);
							datatypeString = line;
						}
					}
					resource = new Resource(nameClassString, new Predicate(predicateString, new ObjectMap(referenceString, datatypeString)));
					System.out.println(resource);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
}
