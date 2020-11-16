package com.example.graveapp.api;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class GraphModel {

    @SerializedName("edges")
    @Getter
    private Edge[] edges;
    @SerializedName("vertices")
    @Getter
    private Vertex[] vertices;

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

    public class Vertex
    {
        @SerializedName("label")
        @Getter
        private String label;
        @SerializedName("latitude")
        @Getter
        private double latitude;
        @SerializedName("longitude")
        @Getter
        private double longitude;
    }

}
