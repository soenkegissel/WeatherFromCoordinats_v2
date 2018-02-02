package com.rucksack.weathermaps.managers;

import com.rucksack.weathermaps.models.Example;
import com.rucksack.weathermaps.models.WeatherModel;
import com.rucksack.weathermaps.network.OpenWeatherApi;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkManager {

  private OpenWeatherApi api;

  public NetworkManager(OpenWeatherApi api) {
    this.api = api;
  }

  public io.reactivex.Observable<WeatherModel> getWeather(String lat, String lng, String units,
      String key) {
    return api.getData(lat, lng, units, key);
  }

  public io.reactivex.Observable<WeatherModel> getWeatherByCityName(String q, String units, String key) {
    return api.getWeatherByCityName(q, units, key);
  }

  public io.reactivex.Observable<Example> getWeatherForCeveralCities(String id, String units, String key) {
    return api.getWeatherForCeveralCities(id, units, key);
  }

  public static <T> ObservableTransformer<T, T> applyObservableAsync() {
    return upstream -> upstream.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }


}
