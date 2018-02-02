package com.rucksack.weathermaps.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Sorry for this code from Railian Maksym (30.11.2017).
 */


public class Example implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.cnt);
        dest.writeList(this.list);
    }

    public Example() {
    }

    protected Example(Parcel in) {
        this.cnt = (Integer) in.readValue(Integer.class.getClassLoader());
        this.list = new ArrayList<WeatherModel>();
        in.readList(this.list, WeatherModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<Example> CREATOR = new Parcelable.Creator<Example>() {
        @Override
        public Example createFromParcel(Parcel source) {
            return new Example(source);
        }

        @Override
        public Example[] newArray(int size) {
            return new Example[size];
        }
    };
}