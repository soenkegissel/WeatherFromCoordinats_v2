package com.example.maks2.weathertocoordinats;

import android.app.Application;

import com.example.maks2.weathertocoordinats.network.OpenWeatherApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Sorry for this code from Railian Maksym (10.07.17).
 */

public class MyApplication extends Application {
    private static OpenWeatherApi openWeatherApi;

    @Override
    public void onCreate() {
        super.onCreate();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BaseUrl))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeatherApi = retrofit.create(OpenWeatherApi.class);
    }

    public static OpenWeatherApi getApi() {
        return openWeatherApi;
    }
}
