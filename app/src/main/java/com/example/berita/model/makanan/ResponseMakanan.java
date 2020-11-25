package com.example.berita.model.makanan;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ResponseMakanan{

	@SerializedName("categories")
	private List<MealsItem> categories;

	public void setCategories(List<MealsItem> categories){
		this.categories = categories;
	}

	public List<MealsItem> getCategories(){
		return categories;
	}

	@Override
 	public String toString(){
		return 
			"ResponseMakanan{" + 
			"categories = '" + categories + '\'' + 
			"}";
		}
}