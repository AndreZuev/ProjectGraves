package com.example.graveapp.api;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class GraveyardModel {

    @SerializedName("blocks")
    @Getter
    private GraveyardBlock[] blocks;

    public class GraveyardBlock
    {
        @SerializedName("id")
        @Getter
        private String id;
        @SerializedName("latitude")
        @Getter
        private double latitude;
        @SerializedName("longitude")
        @Getter
        private double longitude;
        @SerializedName("lots")
        @Getter
        private GraveyardLot[] lots;
    }

    public class GraveyardLot
    {
        @SerializedName("lotNumber")
        @Getter
        private int lotNumber;
        @SerializedName("graves")
        @Getter
        private Grave[] graves;
    }

    public class Grave
    {
        @SerializedName("name")
        @Getter
        private String name;
        @SerializedName("dateOfBirth")
        @Getter
        private String dateOfBith;
        @SerializedName("age")
        @Getter
        private String age;
    }


}
