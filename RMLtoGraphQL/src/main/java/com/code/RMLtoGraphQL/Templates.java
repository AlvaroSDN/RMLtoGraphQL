package com.code.RMLtoGraphQL;

import java.util.List;

public class Templates {
	
	public Templates() {
		super();
	}
	
	String getSchemaTemplate(List<Resource> resources) {
		String result = "schema {\n\tquery: Query\n\tmutation: Mutation\n}\n\n"
				+ "type Query {\n";
		
		for(int i = 1; i < resources.size()+1; i++) {
			result += "\tall<resourceName" + i + ">s(filter: <resourceName" + i + ">Filter, "
					+ "skip: Int = 0, first: Int = 0): [<resourceName" + i + ">]\n";
		}
		result += "}\n\ntype Mutation {\n";
		
		for(int i = 1; i < resources.size()+1; i++) {
			result += "\tcreate<resourceName" + i + ">(";
			for(int j = 1; j < resources.get(i-1).getPredicate().size()+1; j++) {
				result += "<predicateName" + i+j + ">: <datatype" + i+j + ">!, ";
			}
			result = result.substring(0, result.length()-2);
			result += "): <resourceName" + i + ">\n";
		}
		result += "}\n\n";
		
		for(int i = 1; i < resources.size()+1; i++) {
			result += "type <resourceName" + i + "> {\n\tid: ID!\n";
			for(int j = 1; j < resources.get(i-1).getPredicate().size()+1; j++) {
				result += "\t<predicateName" + i+j + ">: <datatype" + i+j + ">!\n";
			}
			result += "}\n\n";
		}
		return result;
	}
}
