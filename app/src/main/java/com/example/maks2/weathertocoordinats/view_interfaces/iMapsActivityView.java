package com.example.maks2.weathertocoordinats.view_interfaces;

import com.arellomobile.mvp.MvpView;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.google.android.gms.maps.MapView;

/**
 * Sorry for this code from Railian Maksym (16.11.2017).
 */

public interface iMapsActivityView extends MvpView {
    void showWeather(WeatherModel weatherModel);
    void showMessage(String msg);
    void showProgress();
    void hideProgress();
}
