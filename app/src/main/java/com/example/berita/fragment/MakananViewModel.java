package com.example.berita.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.berita.model.makanan.MealsItem;
import com.example.berita.model.makanan.ResponseMakanan;
import com.example.berita.network.ApiClientFood;
import com.example.berita.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananViewModel extends ViewModel {
    private static final String API_KEY ="";
    private MutableLiveData<List<MealsItem>> listmovies = new MutableLiveData<>();

    MutableLiveData <List<MealsItem>> getListmakanan(){
        return listmovies;


    }

    void setListMovies() {
        ApiInterface apiInterface = ApiClientFood.getClient().create(ApiInterface.class);
        Call<ResponseMakanan> movieCall = apiInterface.getMakanan(API_KEY);
        movieCall.enqueue(new Callback<ResponseMakanan>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMakanan> call, @NonNull Response<ResponseMakanan> response) {
                if (response.body() != null) {
                    listmovies.postValue(response.body().getCategories());
                    Log.d("onResponseMakanan ", response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMakanan> call, @NonNull Throwable t) {
            Log.d("onFailureMakanan ", t.getMessage());
        }
    });
}
}
