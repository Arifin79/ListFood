package com.example.berita.model.makanan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class MealsItem implements Parcelable {

	@SerializedName("strCategory")
	private String strCategory;

	@SerializedName("strCategoryDescription")
	private String strCategoryDescription;

	@SerializedName("idCategory")
	private int idCategory;

	@SerializedName("strCategoryThumb")
	private String strCategoryThumb;

	public MealsItem(Parcel in) {
		this.strCategory = in.readString();
		this.strCategoryDescription = in.readString();
		this.idCategory = in.readInt();
		this.strCategoryThumb = in.readString();
	}

	public static final Parcelable.Creator<MealsItem> CREATOR = new Parcelable.Creator<MealsItem>() {
		@Override
		public MealsItem createFromParcel(Parcel in) {
			return new MealsItem(in);
		}

		@Override
		public MealsItem[] newArray(int size) {
			return new MealsItem[size];
		}
	};

    public MealsItem() {
    }

	public void setStrCategory(String strCategory){
		this.strCategory = strCategory;
	}

	public String getStrCategory(){
		return strCategory;
	}

	public void setStrCategoryDescription(String strCategoryDescription){
		this.strCategoryDescription = strCategoryDescription;
	}

	public String getStrCategoryDescription(){
		return strCategoryDescription;
	}

	public void setIdCategory(int idCategory){
		this.idCategory = idCategory;
	}

	public int getIdCategory(){
		return idCategory;
	}

	public void setStrCategoryThumb(String strCategoryThumb){
		this.strCategoryThumb = strCategoryThumb;
	}

	public String getStrCategoryThumb(){
		return strCategoryThumb;
	}

	@Override
 	public String toString(){
		return 
			"MealsItem{" +
			"strCategory = '" + strCategory + '\'' + 
			",strCategoryDescription = '" + strCategoryDescription + '\'' + 
			",idCategory = '" + idCategory + '\'' + 
			",strCategoryThumb = '" + strCategoryThumb + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.strCategory);
		dest.writeString(this.strCategoryDescription);
		dest.writeInt(this.idCategory);
		dest.writeString(this.strCategoryThumb);
	}

}