package com.code.RMLtoGraphQL;

public class Templates {
	
	public Templates() {
		super();
	}
	
	String getSchemaTemplate(int numberResources) {
		String result = "schema {\n\tquery: Query\n\tmutation: Mutation\n}\n\n"
				+ "type Query {\n";
		for(int i = 1; i < numberResources+1; i++) {
			result += "\tall<resourceName" + i + ">s(filter: <resourceName" + i + ">Filter, "
					+ "skip: Int = 0, first: Int = 0): [<resourceName" + i + ">]\n";
		}
		result += "}\n\n";
		return result;
	}
}
