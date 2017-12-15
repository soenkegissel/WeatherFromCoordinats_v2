package com.example.maks2.weathertocoordinats.view_interfaces;

import com.arellomobile.mvp.MvpView;
import com.example.maks2.weathertocoordinats.models.Weather;
import com.example.maks2.weathertocoordinats.models.WeatherModel;

import java.util.List;

/**
 * Sorry for this code from Railian Maksym (21.11.2017).
 */

public interface iFavoritesActivityView extends MvpView {
    void showWeather(List<WeatherModel> weatherModels);
    void refreshWeather(int position, WeatherModel weatherModel);
    void showProgress();
    void hideProgress();
    void showMessage(String message);
}
