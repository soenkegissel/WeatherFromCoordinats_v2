package com.example.maks2.weathertocoordinats.network;

import com.example.maks2.weathertocoordinats.models.WeatherModel;

import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Sorry for this code from Railian Maksym (10.07.17).
 */

public interface OpenWeatherApi {
    @Headers("Content-Type: application/json; charset=UTF-8")
    @GET("/data/2.5/weather")
    rx.Observable<WeatherModel> getData(@Query("lat") String lat, @Query("lon") String lng, @Query("appid") String appid);
}
