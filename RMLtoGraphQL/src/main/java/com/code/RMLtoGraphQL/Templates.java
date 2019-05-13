package com.code.RMLtoGraphQL;

import java.util.List;

public class Templates {

	public Templates() {
		super();
	}

	public String getSchemaTemplate(List<Resource> resources) {
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

	public String getResourceClassTemplate(Resource resource) {
		return "package com.servidor.graphql;\n\n\npublic class <resourceName> {\n\n" + 
				this.getAttributesResource(resource) + "\n" + this.getConstructorResource(resource) +
				this.getGettersResource(resource) + "}";
	}

	private String getAttributesResource(Resource resource) {
		String result = "\tprivate final String id;";
		for(int i = 1; i < resource.getPredicate().size()+1; i++) {
			result += "\n\tprivate final <datatype" + i + "> <predicateName" + i + ">;";
		}
		return result + "\n";
	}

	private String getConstructorResource(Resource resource) {
		String constructor1 = "\tpublic <resourceName>(";
		String constructor2 = "\tpublic <resourceName>(String id, ";
		String constructor3 = "\t\tthis(null, ";
		String constructor4 = "\n\t\tthis.id = id;";
		for(int i = 1; i < resource.getPredicate().size()+1; i++) {
			constructor1 += "<datatype" + i + "> <predicateName" + i + ">, ";
			constructor2 += "<datatype" + i + "> <predicateName" + i + ">, ";
			constructor3 += "<predicateName" + i + ">, ";
			constructor4 += "\n\t\tthis.<predicateName" + i + "> = <predicateName" + i + ">;";
		}
		constructor1 = constructor1.substring(0, constructor1.length()-2);
		constructor1 += ") {\n";
		constructor2 = constructor2.substring(0, constructor2.length()-2);
		constructor2 += ") {";
		constructor3 = constructor3.substring(0, constructor3.length()-2);
		constructor3 += ");";
		return constructor1 + constructor3 + "\n\t}\n\n" + constructor2 + constructor4 + "\n\t}\n";
	}

	private String getGettersResource(Resource resource) {
		String getters = "";
		for(int i = 1; i < resource.getPredicate().size()+1; i++) {
			getters += "\n\tpublic <datatype" + i + "> get<predicateName" + i + ">() {\n\t\treturn <predicateName" + i + ">;\n\t}\n";
		}
		return getters;
	}

	private String getttributesResource(Resource resource) {
		String result = "\tprivate final String id;";
		for(int i = 1; i < resource.getPredicate().size()+1; i++) {
			result += "\n\tprivate final <datatype" + i + "> <predicateName" + i + ">;";
		}
		return result;
	}
}
