package com.example.berita.model.minuman;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMinuman{

	@SerializedName("drinks")
	private List<DrinksItem> drinks;

	public void setDrinks(List<DrinksItem> drinks){
		this.drinks = drinks;
	}

	public List<DrinksItem> getDrinks(){
		return drinks;
	}

	@Override
 	public String toString(){
		return 
			"ResponseMinuman{" + 
			"drinks = '" + drinks + '\'' + 
			"}";
		}
}