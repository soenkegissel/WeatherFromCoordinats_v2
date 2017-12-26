package com.example.maks2.weathertocoordinats.managers;

import com.example.maks2.weathertocoordinats.MyApplication;
import com.example.maks2.weathertocoordinats.models.Example;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import rx.schedulers.Schedulers;

/**
 * Sorry for this code from Railian Maksym (24.09.2017).
 */

public class NetworkManager {
    public static rx.Observable<WeatherModel> getWeather(String lat, String lng, String units, String key) {
        final Gson gson = new GsonBuilder()
                .create();
        return MyApplication.getApi().getData(lat, lng, units, key)
                .subscribeOn(Schedulers.io());
    }
    public static rx.Observable<WeatherModel>getWeatherByCityName(String q, String units, String key){
        final Gson gson = new GsonBuilder()
                .create();
        return MyApplication.getApi().getWeatherByCityName(q, units, key)
                .subscribeOn(Schedulers.io());
    }
    public static rx.Observable<Example>getWeatherForCeveralCities(String id, String units, String key){
        final Gson gson = new GsonBuilder()
                .create();
        return MyApplication.getApi().getWeatherForCeveralCities(id, units, key)
                .subscribeOn(Schedulers.io());
    }
}
