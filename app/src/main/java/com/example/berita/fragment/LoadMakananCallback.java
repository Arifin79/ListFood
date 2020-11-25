package com.example.berita.fragment;

import com.example.berita.model.makanan.MealsItem;

import java.util.ArrayList;

public interface LoadMakananCallback {
    void preExecute();

    void postExecute(ArrayList<MealsItem> mealsItems);
}
