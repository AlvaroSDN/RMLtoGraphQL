package com.code.RMLtoGraphQL;

import java.util.List;

public class Resource  {
	private String nameClass;
	private List<Predicate> predicates;
	
	public Resource(String nameClass, List<Predicate> predicates) {
		this.nameClass = nameClass;
		this.predicates = predicates;
	}
	
	public String getNameClass() {
		return nameClass;
	}
	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}
	public List<Predicate> getPredicate() {
		return predicates;
	}
	public void setPredicate(List<Predicate> predicates) {
		this.predicates = predicates;
	}

	@Override
	public String toString() {
		String result = "Resource \nnameClass = " + nameClass + "\n";
		for(int i = 0; i < predicates.size(); i++) {
			result += "predicate " + i + " = " + predicates.get(i) + "\n";
		}
		return result;
	}
	
}
