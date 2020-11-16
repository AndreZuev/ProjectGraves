package com.example.graveapp.api;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class EdgesModel {

    @SerializedName("vertices")
    @Getter
    private int vertices;
    @SerializedName("edges")
    @Getter
    private Edge[] edges;

    public class Edge
    {
        @SerializedName("src")
        @Getter
        private String source;
        @SerializedName("dest")
        @Getter
        private String destination;
        @SerializedName("weight")
        @Getter
        private double weight;
    }

}
