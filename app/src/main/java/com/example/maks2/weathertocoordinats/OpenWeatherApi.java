package com.example.maks2.weathertocoordinats;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fluffy on 10.07.17.
 */

public interface OpenWeatherApi {
    @GET("/data/2.5/weather")
    Call<WeatherModel> getData(@Query("lat") String lat,@Query("lon") String lng,@Query("appid") String appid);
}
