package com.code.RMLtoGraphQL;

public class ObjectMap {
	private String reference;
	private String datatype;
	private String template;
	
	public ObjectMap() {
		super();
	}
	
	public ObjectMap(String reference, String datatype, String template) {
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

	@Override
	public String toString() {
		return "ObjectMap\n[reference=" + reference + ", \ndatatype=" + datatype + ", \ntemplate=" + template + "]";
	}
	
	
}
