package com.example.berita.fragment;
import com.example.berita.model.minuman.DrinksItem;

import java.util.ArrayList;

public interface LoadMinumanCallBack {
    void preExecute();

    void postExecute(ArrayList<DrinksItem> drinksItems);
}
