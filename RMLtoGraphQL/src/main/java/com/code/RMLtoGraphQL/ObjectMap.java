package com.code.RMLtoGraphQL;

public class ObjectMap {
	private String reference;
	private String datatype;
	public ObjectMap(String reference, String datatype) {
		this.reference = reference;
		this.datatype = datatype;
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
	
	@Override
	public String toString() {
		return "ObjectMap [reference=" + reference + ", datatype=" + datatype + "]";
	}
	
	
}
