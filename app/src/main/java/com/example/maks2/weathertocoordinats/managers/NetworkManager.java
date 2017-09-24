package com.example.maks2.weathertocoordinats.managers;

import com.example.maks2.weathertocoordinats.MyApplication;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rx.schedulers.Schedulers;

/**
 * Sorry for this code from Railian Maksym (24.09.2017).
 */

public class NetworkManager {
    public static rx.Observable<WeatherModel> getWeather(String lat, String lng, String key) {
        final Gson gson = new GsonBuilder()
                .create();
        return MyApplication.getApi().getData(lat, lng, key)
                .subscribeOn(Schedulers.io())
                .map(map -> map);
    }
}
