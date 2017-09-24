
package com.example.maks2.weathertocoordinats.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;

    public String getCountry() {
        return country;
    }


    public Integer getSunrise() {
        return sunrise;
    }


    public Integer getSunset() {
        return sunset;
    }


}
