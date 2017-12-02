package com.example.maks2.weathertocoordinats.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Sorry for this code from Railian Maksym (30.11.2017).
 */


public class Example implements Serializable {

    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherModel> list = null;

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<WeatherModel> getList() {
        return list;
    }

    public void setList(java.util.List<WeatherModel> list) {
        this.list = list;
    }

}