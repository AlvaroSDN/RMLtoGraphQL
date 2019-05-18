package com.code.RMLtoGraphQL;

import java.util.List;

public class Resource  {
	private String nameClass;
	private String templateMain;
	private List<Predicate> predicates;
	private String iterator;
	private boolean haveRelation;
	
	public Resource(String nameClass, String templateMain, String iterator, List<Predicate> predicates, boolean haveRelation) {
		this.nameClass = nameClass;
		this.templateMain = templateMain;
		this.iterator = iterator;
		this.predicates = predicates;
		this.haveRelation = haveRelation;
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

	public String getTemplateMain() {
		return templateMain;
	}

	public void setTemplateMain(String templateMain) {
		this.templateMain = templateMain;
	}
	
	public boolean isHaveRelation() {
		return haveRelation;
	}

	public void setHaveRelation(boolean haveRelation) {
		this.haveRelation = haveRelation;
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
