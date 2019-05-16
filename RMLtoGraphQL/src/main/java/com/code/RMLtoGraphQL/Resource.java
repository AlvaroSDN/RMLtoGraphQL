package com.code.RMLtoGraphQL;

import java.util.List;

public class Resource  {
	private String nameClass;
	private List<Predicate> predicates;
	private String iterator;
	
	public Resource(String nameClass, String iterator, List<Predicate> predicates) {
		this.nameClass = nameClass;
		this.iterator = iterator;
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

	public String getIterator() {
		return iterator;
	}

	public void setIterator(String iterator) {
		this.iterator = iterator;
	}

	@Override
	public String toString() {
		String result = "Resource \nnameClass = " + nameClass + "\n" + "iterator = " + iterator + "\n";
		for(int i = 0; i < predicates.size(); i++) {
			result += "predicate " + i + " = " + predicates.get(i) + "\n";
		}
		return result;
	}
	
}
