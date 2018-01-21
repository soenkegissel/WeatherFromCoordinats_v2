package com.example.maks2.weathertocoordinats.managers;

import com.example.maks2.weathertocoordinats.models.Example;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.network.OpenWeatherApi;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkManager {

  private OpenWeatherApi api;

  public NetworkManager(OpenWeatherApi api) {
    this.api = api;
  }

  public io.reactivex.Observable<WeatherModel> getWeather(String lat, String lng, String units,
      String key) {
    return api.getData(lat, lng, units, key)
        .subscribeOn(Schedulers.io());
  }

  public io.reactivex.Observable<WeatherModel> getWeatherByCityName(String q, String units, String key) {
    return api.getWeatherByCityName(q, units, key)
        .subscribeOn(Schedulers.io());
  }

  public io.reactivex.Observable<Example> getWeatherForCeveralCities(String id, String units, String key) {
    return api.getWeatherForCeveralCities(id, units, key)
        .subscribeOn(Schedulers.io());
  }

  public static <T> ObservableTransformer<T, T> applyObservableAsync() {
    return upstream -> upstream.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }


}
