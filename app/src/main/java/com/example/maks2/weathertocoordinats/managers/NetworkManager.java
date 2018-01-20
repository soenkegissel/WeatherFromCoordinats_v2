package com.example.maks2.weathertocoordinats.managers;

import com.example.maks2.weathertocoordinats.models.Example;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.network.OpenWeatherApi;
import java.util.Optional;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NetworkManager {

    private OpenWeatherApi api;

    public NetworkManager(OpenWeatherApi api) {
        this.api = api;
    }

    public rx.Observable<WeatherModel> getWeather(String lat, String lng, String units, String key) {
        return api.getData(lat, lng, units, key);
    }

    public rx.Observable<WeatherModel> getWeatherByCityName(String q, String units, String key) {
        return api.getWeatherByCityName(q, units, key);
    }

    public rx.Observable<Example> getWeatherForCeveralCities(String id, String units, String key) {
        return api.getWeatherForCeveralCities(id, units, key);
    }

    public static <T> Transformer<T,T> addObservableParameters() {
        return observable -> observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io());
    }

}
