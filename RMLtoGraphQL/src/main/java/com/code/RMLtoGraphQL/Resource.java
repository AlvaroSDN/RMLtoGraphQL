package com.code.RMLtoGraphQL;

public class Resource  {
	private String nameClass;
	private Predicate predicate;
	
	public Resource(String nameClass, Predicate predicate) {
		this.nameClass = nameClass;
		this.predicate = predicate;
	}
	
	public String getNameClass() {
		return nameClass;
	}
	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}
	public Predicate getPredicate() {
		return predicate;
	}
	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public String toString() {
		return "Resource [nameClass=" + nameClass + ", predicate=" + predicate + "]";
	}
	
}
