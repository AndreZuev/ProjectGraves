package com.example.graveapp.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ApiClient {

    public static final String BASE_URL = "https://grog.dcaron.xyz";

    private static Retrofit instance;

    public static Retrofit getRetrofitInstance(Context context) {
        if (instance == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .build();
        }
        return instance;
    }

}

