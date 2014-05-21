package com.mobile.promo.model;

public class ExpandListChild {

	private String Name;
	private String Tag;
	
	public ExpandListChild() {
	}
	
	public ExpandListChild(String name, String tag) {
		super();
		Name = name;
		Tag = tag;
	}
	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		this.Name = Name;
	}
	public String getTag() {
		return Tag;
	}
	public void setTag(String Tag) {
		this.Tag = Tag;
	}
}


