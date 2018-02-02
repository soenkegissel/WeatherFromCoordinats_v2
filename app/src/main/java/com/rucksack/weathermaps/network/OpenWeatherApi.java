package com.rucksack.weathermaps.network;

import com.rucksack.weathermaps.models.Example;
import com.rucksack.weathermaps.models.WeatherModel;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface OpenWeatherApi {
    @GET("/data/2.5/weather")
    io.reactivex.Observable<WeatherModel> getData(@Query("lat") String lat, @Query("lon") String lng, @Query("units") String units, @Query("appid") String appid);

    @GET("/data/2.5/weather")
    io.reactivex.Observable<WeatherModel> getWeatherByCityName(@Query("q") String q, @Query("units") String units, @Query("appid") String appid);

    @GET("/data/2.5/group")
    io.reactivex.Observable<Example> getWeatherForCeveralCities(@Query("id") String id, @Query("units") String units, @Query("appid") String appid);
}
