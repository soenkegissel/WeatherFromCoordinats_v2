package com.rucksack.weathermaps.view_interfaces;

import com.arellomobile.mvp.MvpView;
import com.rucksack.weathermaps.models.WeatherModel;

import java.util.List;


public interface iFavoritesActivityView extends MvpView {
    void showWeather(List<WeatherModel> weatherModels);
    void refreshWeather(int position, WeatherModel weatherModel);
    void showProgress();
    void hideProgress();
    void showMessage(String message);
}
