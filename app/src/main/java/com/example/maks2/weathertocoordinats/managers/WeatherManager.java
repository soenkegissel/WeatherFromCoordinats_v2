package com.example.maks2.weathertocoordinats.managers;

import android.content.Context;
import android.widget.Toast;

import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.models.WeatherModel;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Sorry for this code from Railian Maksym (15.11.2017).
 */

public class WeatherManager {
    private WeatherModel weatherModel;
    private Context context;

    public WeatherManager(Context context) {
        this.context = context;
    }

    public WeatherModel getWeatherByCoordinates(String lat, String lng) {
         NetworkManager.getWeather(lat, lng, context.getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeatherModel weatherModelData) {
                        weatherModel = weatherModelData;
                    }
                });

        return weatherModel;
    }

    public WeatherModel getWeatherByName(String name){
        NetworkManager.getWeatherByCityName(name,context.getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeatherModel weatherModelData) {
                    weatherModel=weatherModelData;
                    }
                });
        return weatherModel;
    }
}
