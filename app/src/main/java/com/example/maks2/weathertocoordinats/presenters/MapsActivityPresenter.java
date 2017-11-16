package com.example.maks2.weathertocoordinats.presenters;

import android.content.Context;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.managers.NetworkManager;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.view_interfaces.iMapsActivityView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Sorry for this code from Railian Maksym (16.11.2017).
 */
@InjectViewState
public class MapsActivityPresenter extends BasePresenter<iMapsActivityView> {
    Context context;
    private WeatherModel weatherModel;

    public MapsActivityPresenter(Context context) {
        this.context = context;
    }

    public void getWeatherByCoordinates(String lat, String lng) {
        Subscription getWeatherByCoordinates = NetworkManager.getWeather(lat, lng, context.getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .filter(weatherModelData -> weatherModelData.getWind().getDeg() != null)
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showWeather(weatherModel);
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
        unsubscribeOnDestroy(getWeatherByCoordinates);
    }

    public void getWeatherByName(String name) {

        Subscription getWeatherByName = NetworkManager.getWeatherByCityName(name, context.getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .filter(weatherModelData -> weatherModelData.getWind().getDeg() != null)
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showWeather(weatherModel);
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

        unsubscribeOnDestroy(getWeatherByName);
    }
}
