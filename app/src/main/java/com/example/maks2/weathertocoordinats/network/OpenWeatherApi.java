package com.example.maks2.weathertocoordinats.network;

import com.example.maks2.weathertocoordinats.models.Example;
import com.example.maks2.weathertocoordinats.models.WeatherModel;

import io.reactivex.Flowable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface OpenWeatherApi {
    @GET("/data/2.5/weather")
    io.reactivex.Observable<WeatherModel> getData(@Query("lat") String lat, @Query("lon") String lng, @Query("units") String units, @Query("appid") String appid);

    @GET("/data/2.5/weather")
    io.reactivex.Observable<WeatherModel> getWeatherByCityName(@Query("q") String q, @Query("units") String units, @Query("appid") String appid);

    @GET("/data/2.5/group")
    io.reactivex.Observable<Example> getWeatherForCeveralCities(@Query("id") String id, @Query("units") String units, @Query("appid") String appid);
}
