package com.example.berita.model.minuman;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DrinksItem implements Parcelable {

	@SerializedName("strDrink")
	private String strDrink;

	@SerializedName("strDrinkThumb")
	private String strDrinkThumb;

	@SerializedName("idDrink")
	private int idDrink;

	public DrinksItem(Parcel in) {
		strDrink = in.readString();
		strDrinkThumb = in.readString();
		idDrink = in.readInt();
	}

	public static final Parcelable.Creator<DrinksItem> CREATOR = new Parcelable.Creator<DrinksItem>() {
		@Override
		public DrinksItem createFromParcel(Parcel in) {
			return new DrinksItem(in);
		}

		@Override
		public DrinksItem[] newArray(int size) {
			return new DrinksItem[size];
		}
	};

    public DrinksItem() {
    }

	public void setStrDrink(String strDrink){
		this.strDrink = strDrink;
	}

	public String getStrDrink(){
		return strDrink;
	}

	public void setStrDrinkThumb(String strDrinkThumb){
		this.strDrinkThumb = strDrinkThumb;
	}

	public String getStrDrinkThumb(){
		return strDrinkThumb;
	}

	public void setIdDrink(int idDrink){
		this.idDrink = idDrink;
	}

	public int getIdDrink(){
		return idDrink;
	}

	@Override
 	public String toString(){
		return 
			"DrinksItem{" + 
			"strDrink = '" + strDrink + '\'' + 
			",strDrinkThumb = '" + strDrinkThumb + '\'' + 
			",idDrink = '" + idDrink + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(strDrink);
		dest.writeString(strDrinkThumb);
		dest.writeInt(idDrink);
	}
}