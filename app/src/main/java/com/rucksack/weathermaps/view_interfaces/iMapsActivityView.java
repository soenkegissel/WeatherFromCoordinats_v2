package com.rucksack.weathermaps.view_interfaces;

import com.arellomobile.mvp.MvpView;
import com.rucksack.weathermaps.models.WeatherModel;

public interface iMapsActivityView extends MvpView {
    void showWeather(WeatherModel weatherModel);
    void showMessage(String msg);
    void showProgress();
    void hideProgress();
}
