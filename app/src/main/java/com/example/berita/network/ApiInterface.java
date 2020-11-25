package com.example.berita.network;

import com.example.berita.model.makanan.ResponseMakanan;
import com.example.berita.model.minuman.ResponseMinuman;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("categories.php")
    Call<ResponseMakanan> getMakanan(@Query("api_key") String apikey);

    @GET("filter.php?a=Non_Alcoholic")
    Call<ResponseMinuman> getMinuman(@Query("api_key") String apiKey);
}
