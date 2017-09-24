package com.example.maks2.weathertocoordinats.managers;

import com.example.maks2.weathertocoordinats.MyApplication;
import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.Map;
import java.util.Observable;

import retrofit2.Retrofit;
import rx.schedulers.Schedulers;

/**
 * Created by maks2 on 24.09.2017.
 */

public class NetworkManager {
    public static rx.Observable<WeatherModel> getWeather(String lat, String lng, String key) {
        final Gson gson = new GsonBuilder()
                .create();
       return MyApplication.getApi().getData(lat,lng,key)
                .subscribeOn(Schedulers.io())
                .map(map->{
                  /*  Map data=map;
                    String dataGson=gson.toJson(data);
                    WeatherModel weatherModel=gson.fromJson(dataGson,WeatherModel.class);*/
                    return map;
                });
    }
}
