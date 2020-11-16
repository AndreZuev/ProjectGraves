package com.example.graveapp.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MapsAPI {

    @GET("/Graveyard")
    Call<GraveyardModel> getGraveyard();

    @GET("/Graph")
    Call<GraphModel> getGraph();

}
