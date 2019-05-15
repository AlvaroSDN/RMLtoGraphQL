package com.code.RMLtoGraphQL;

import java.util.List;

public class Templates {

	public Templates() {
		super();
	}

	public String getQueryTemplate(List<Resource> resources) {
		String result = "package com.servidorGraphQL.code;\r\n" + 
				"\r\n" + 
				"import java.util.List;\r\n" + 
				"\r\n" + 
				"import com.coxautodev.graphql.tools.GraphQLRootResolver;\r\n" + 
				"\r\n" + 
				"public class Query implements GraphQLRootResolver {\r\n\n";
		String variables = "";
		String constructor = "\tpublic Query(";
		String constructor2 = "";
		String queries = "";
		
		for(int i = 1; i < resources.size()+1; i++) {
			variables += "\tprivate final <resourceName" + i + ">Repository <resourceVarName" + i + ">Repository;\r\n";
			constructor += "<resourceName" + i + ">Repository <resourceVarName" + i + ">Repository,\r\n";
			constructor2 += "\t\tthis.<resourceVarName" + i + ">Repository = <resourceVarName" + i + ">Repository;\r\n";
			queries += "\tpublic List<init><resourceName" + i + "><end> all<resourceName" + i + ">s() {\r\n" + 
					"\t\treturn <resourceVarName" + i + ">Repository.getAll<resourceName" + i + ">s();\r\n" + 
					"\t}\r\n\n";
		}
		constructor = constructor.substring(0, constructor.length()-3);
		constructor += ") {\r\n";
		constructor2 += "\t}\r\n\n";
		result += variables + "\n" + constructor + constructor2 + queries + "}";
		return result;
	}
	
	public String getMutationTemplate(List<Resource> resources) {
		String result = "package com.servidorGraphQL.code;\r\n" + 
				"\r\n" + 
				"import com.coxautodev.graphql.tools.GraphQLRootResolver;\r\n" + 
				"\r\n" + 
				"public class Mutation implements GraphQLRootResolver {\r\n\n";
		String variables = "";
		String constructor = "\tpublic Mutation(";
		String constructor2 = "";
		String mutations = "";
		String arguments1 = null;
		String arguments2 = null;
		
		for(int i = 1; i < resources.size()+1; i++) {
			variables += "\tprivate final <resourceName" + i + ">Repository <resourceVarName" + i + ">Repository;\r\n";
			constructor += "<resourceName" + i + ">Repository <resourceVarName" + i + ">Repository,\r\n";
			constructor2 += "\t\tthis.<resourceVarName" + i + ">Repository = <resourceVarName" + i + ">Repository;\r\n";
			
			arguments1 = "(";
			arguments2 = "(";
			for(int j = 1; j < resources.get(i-1).getPredicate().size()+1; j++) {
				arguments1 += "<datatype" + i+j + "> <predicateName" + i+j + ">, ";
				arguments2 += "<predicateName" + i+j + ">, ";
			}
			arguments1 = arguments1.substring(0, arguments1.length()-2);
			arguments1 += ") {\r\n";
			arguments2 = arguments2.substring(0, arguments2.length()-2);
			arguments2 += ");\r\n";
			mutations += "\tpublic <resourceName" + i + "> create<resourceName" + i + ">" + arguments1 +  
					"\t\t<resourceName" + i + "> new<resourceName" + i + "> = new <resourceName" + i + ">" + arguments2 + 
					"\t\treturn <resourceVarName" + i + ">Repository.save<resourceName" + i + ">(new<resourceName" + i + ">);\r\n" + 
					"\t}\r\n\n";
		}
		constructor = constructor.substring(0, constructor.length()-3);
		constructor += ") {\r\n";
		constructor2 += "\t}\r\n\n";
		result += variables + "\n" + constructor + constructor2 + mutations + "}";
		return result;
	}
	
	public String getSchemaTemplate(List<Resource> resources) {
		String result = "schema {\n\tquery: Query\n\tmutation: Mutation\n}\n\n"
				+ "type Query {\n";

		for(int i = 1; i < resources.size()+1; i++) {
			result += "\tall<resourceName" + i + ">s: [<resourceName" + i + ">]\n";
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
		return "package com.servidorGraphQL.code;\n\n\npublic class <resourceName> {\n\n" + 
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
			getters += "\n\tpublic <datatype" + i + "> get<predicateGetterName" + i + ">() {\n\t\treturn <predicateName" + i + ">;\n\t}\n";
		}
		return getters;
	}

	public String getResourceRepositoryTemplate(Resource resource) {
		return "package com.servidorGraphQL.code;\n\n" +
				this.getRepositoryImports() +
				"\n\n" +
				"public class <resourceName>Repository {\r\n" + 
				"\r\n" + 
				"\tprivate final MongoCollection<init>Document<end> <resourceVarName>s;\r\n" + 
				"\r\n" + 
				"\tpublic <resourceName>Repository(MongoCollection<init>Document<end> <resourceVarName>s) {\r\n" + 
				"\t\tthis.<resourceVarName>s = <resourceVarName>s;\r\n" + 
				"\t}\r\n\n" +
				this.getRepositoryFind() + "\n\n" + this.getRepositoryGetAll() + 
				"\n\n" + this.getRepositorySaveAndConstructorResource(resource) + "}";
	}

	private String getRepositoryImports() {
		return "import com.mongodb.client.FindIterable;\r\n" + 
				"import com.mongodb.client.MongoCollection;\r\n" + 
				"\r\n" + 
				"import org.bson.Document;\r\n" + 
				"import org.bson.conversions.Bson;\r\n" + 
				"import org.bson.types.ObjectId;\r\n" + 
				"\r\n" + 
				"import java.util.ArrayList;\r\n" + 
				"import java.util.List;\r\n" + 
				"import java.util.Optional;\r\n" + 
				"\r\n" + 
				"import static com.mongodb.client.model.Filters.and;\r\n" + 
				"import static com.mongodb.client.model.Filters.eq;\r\n" + 
				"import static com.mongodb.client.model.Filters.regex;";
	}

	private String getRepositoryFind() {
		return "\tpublic <resourceName> findById(String id) {\r\n" + 
				"\tDocument doc = <resourceVarName>s.find(eq(\"_id\", new ObjectId(id))).first();\r\n" + 
				"\treturn <resourceVarName>(doc);\r\n" + 
				"\t}";
	}

	private String getRepositoryGetAll() {
		return "\tpublic List<init><resourceName><end> getAll<resourceName>s() {\r\n" + 
				"\t\tList<init><resourceName><end> all<resourceName>s = new ArrayList<init><resourceName><end>();\r\n" + 
				"\t\tfor (Document doc : <resourceVarName>s.find()) {\r\n" + 
				"\t\t\tall<resourceName>s.add(<resourceVarName>(doc));\r\n" + 
				"\t\t}\r\n" + 
				"\t\treturn all<resourceName>s;\r\n" + 
				"\t}";
	} 

	/* private String getRepositoryGetAll() {
		return "\tpublic List<<resourceName>> getAll<resourceName>s(<resourceName>Filter filter, int skip, int first) {\r\n" + 
				"\t\tOptional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);\r\n" + 
				"\r\n" + 
				"\t\tList<<resourceName>> all<resourceName>s = new ArrayList<>();\r\n" + 
				"\t\tFindIterable<Document> documents = mongoFilter.map(<resourceVarName>s::find).orElseGet(<resourceVarName>s::find);\r\n" + 
				"\t\tfor (Document doc : documents.skip(skip).limit(first)) {\r\n" + 
				"\t\t\tall<resourceName>.add(<resourceVarName>(doc));\r\n" + 
				"\t\t}\r\n" + 
				"\t\treturn all<resourceName>s;\r\n" + 
				"\t}";
	} */

	private String getRepositorySaveAndConstructorResource(Resource resource) {
		String save = "\tpublic <resourceName> save<resourceName>(<resourceName> <resourceVarName>) {\r\n" + 
				"\t\tDocument doc = new Document();\r\n"; 
		String constructor = "\tprivate <resourceName> <resourceVarName>(Document doc) {\r\n";
		String constructor2	= "\t\treturn new <resourceName>(\r\n" + 
				"\t\t\tdoc.get(\"_id\").toString(),\r\n";

		for(int i = 1; i < resource.getPredicate().size()+1; i++) {
			save += "\t\tdoc.append(\"<referenceName" + i + ">\", <resourceVarName>.get<predicateGetterName" + i + ">());\r\n";
			constructor2 += "\t\t\tdoc.get<datatypeGetterName" + i + ">(\"<referenceName" + i + ">\"),\r\n";
		}
		constructor2 = constructor2.substring(0, constructor2.length()-3);
		constructor2 += ");\r\n";
		save += "\t\t<resourceVarName>s.insertOne(doc);\r\n" + 
				constructor2 +
				"\t}\r\n";
		constructor += constructor2 + 
				"\t}\r\n";
		return save + "\n" + constructor;
	}
}
