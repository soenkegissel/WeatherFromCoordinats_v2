package com.example.maks2.weathertocoordinats.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.maks2.weathertocoordinats.Constants;
import com.example.maks2.weathertocoordinats.managers.NetworkManager;
import com.example.maks2.weathertocoordinats.network.HttpInterceptor;
import com.example.maks2.weathertocoordinats.network.OpenWeatherApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

  private String baseURL;

  public NetworkModule() {
    baseURL = Constants.BASE_URL;

  }


  @Provides
  @Singleton
  Gson provideGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create();
  }

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
        .addInterceptor(new HttpInterceptor())
        .build();
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseURL)
        .client(okHttpClient)
        .build();
  }

  @Provides
  @Singleton
  OpenWeatherApi provideApi(Retrofit retrofit) {
    return retrofit.create(OpenWeatherApi.class);
  }

  @Provides
  @Singleton
  NetworkManager providesNetworkManager(OpenWeatherApi api) {
    return new NetworkManager(api);
  }
}
