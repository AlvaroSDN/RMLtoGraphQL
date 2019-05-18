package com.code.RMLtoGraphQL;

public class ObjectMap {
	private String relation;
	private String reference;
	private String datatype;
	private String template;
	
	public ObjectMap() {
		super();
	}
	
	public ObjectMap(String relation, String reference, String datatype, String template) {
		this.relation = relation;
		this.reference = reference;
		this.datatype = datatype;
		this.template = template;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {
		return "ObjectMap\n[relation=" + relation + ", \nreference=" + reference + ", \ndatatype=" + datatype + ", \ntemplate=" + template + "]";
	}
	
	
}
