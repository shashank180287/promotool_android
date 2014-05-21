package com.mobile.promo.model;

import java.util.ArrayList;

public class ExpandListGroup {
 
	private String Name;
	private int imageResourse;
	private ArrayList<ExpandListChild> Items;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public ArrayList<ExpandListChild> getItems() {
		return Items;
	}
	public void setItems(ArrayList<ExpandListChild> Items) {
		this.Items = Items;
	}
	public void addItem(ExpandListChild item) {
		if(this.Items == null) Items = new ArrayList<ExpandListChild>();
		this.Items.add(item);
	}
	public int getImageResourse() {
		return imageResourse;
	}
	public void setImageResourse(int imageResourse) {
		this.imageResourse = imageResourse;
	}
	
}


