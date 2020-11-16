package com.example.graveapp.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://graves.dcaron.xyz";

    private static Retrofit instance;

    public static Retrofit getRetrofitInstance(Context context) {
        if (instance == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return instance;
    }

}

