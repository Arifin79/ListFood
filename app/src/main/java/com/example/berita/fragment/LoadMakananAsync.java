package com.example.berita.fragment;

import android.os.AsyncTask;

import com.example.berita.db.MakananHelper;
import com.example.berita.model.makanan.MealsItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LoadMakananAsync extends AsyncTask<Void, Void, ArrayList<MealsItem>> {
    private final WeakReference<MakananHelper> makananHelperWeakReference;
    private final WeakReference<LoadMakananCallback> makananCallbackWeakReference;

    LoadMakananAsync(MakananHelper makananHelper, LoadMakananCallback loadMakananCallback) {
        makananHelperWeakReference = new WeakReference<>(makananHelper);
        makananCallbackWeakReference = new WeakReference<>(loadMakananCallback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        makananCallbackWeakReference.get().preExecute();
    }

    @Override
    protected ArrayList<MealsItem> doInBackground(Void... voids) {
        return makananHelperWeakReference.get().getAllMakanan();
    }

    @Override
    protected void onPostExecute(ArrayList<MealsItem> mealsItems) {
        super.onPostExecute(mealsItems);

        makananCallbackWeakReference.get().postExecute(mealsItems);
    }
}
