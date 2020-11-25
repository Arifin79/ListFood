package com.example.berita.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.berita.model.minuman.DrinksItem;
import com.example.berita.model.minuman.ResponseMinuman;
import com.example.berita.network.ApiClientDrinks;
import com.example.berita.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinumanViewModel extends ViewModel {
    private static final String API_KEY ="";
    private MutableLiveData<List<DrinksItem>> listminuman = new MutableLiveData<>();

    MutableLiveData <List<DrinksItem>> getListminuman(){
        return listminuman;


    }

    void setListminuman() {
        ApiInterface apiInterface = ApiClientDrinks.getClient().create(ApiInterface.class);
        Call<ResponseMinuman> movieCall = apiInterface.getMinuman(API_KEY);
        movieCall.enqueue(new Callback<ResponseMinuman>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMinuman> call, @NonNull Response<ResponseMinuman> response) {
                if (response.body() != null) {
                    listminuman.postValue(response.body().getDrinks());
                    Log.d("onResponseMinuman ", response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMinuman> call, @NonNull Throwable t) {
                Log.d("onFailureMinuman ", t.getMessage());
            }
        });
    }
}
