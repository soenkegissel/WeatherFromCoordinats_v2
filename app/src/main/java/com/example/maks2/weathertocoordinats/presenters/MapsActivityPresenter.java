package com.example.maks2.weathertocoordinats.presenters;

import android.content.Context;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.example.maks2.weathertocoordinats.Constants;
import com.example.maks2.weathertocoordinats.MyApplication;
import com.example.maks2.weathertocoordinats.managers.HttpErrorViewManager;
import com.example.maks2.weathertocoordinats.managers.NetworkManager;
import com.example.maks2.weathertocoordinats.managers.RealmDatabaseManager;
import com.example.maks2.weathertocoordinats.models.Location;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.view_interfaces.iMapsActivityView;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Sorry for this code from Railian Maksym (16.11.2017).
 */
@InjectViewState
public class MapsActivityPresenter extends BasePresenter<iMapsActivityView> {
    private Context context;
    private WeatherModel weatherModel;
    @Inject
    Realm realm;
    @Inject
    RealmDatabaseManager realmDatabaseManager;
    @Inject
    NetworkManager networkManager;

    public MapsActivityPresenter(Context context) {
        this.context = context;
        ((MyApplication)context.getApplicationContext()).getAppComponent().inject(this);
    }

    public void getWeatherByCoordinates(String lat, String lng, String units) {
        Subscription getWeatherByCoordinates = networkManager.getWeather(lat, lng, units, Constants.APP_ID)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(weatherModelData -> weatherModelData.getWind().getDeg() != null)
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showWeather(weatherModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showMessage(HttpErrorViewManager.convertToText(context,e.getLocalizedMessage()));
                    }

                    @Override
                    public void onNext(WeatherModel weatherModelData) {
                        weatherModel = weatherModelData;
                    }

                });
        unsubscribeOnDestroy(getWeatherByCoordinates);
    }

    public void getWeatherByName(String name, String units) {

        Subscription getWeatherByName = networkManager.getWeatherByCityName(name, units, Constants.APP_ID)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(weatherModelData -> weatherModelData.getWind().getDeg() != null)
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showWeather(weatherModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, HttpErrorViewManager.convertToText(context,e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeatherModel weatherModelData) {
                        weatherModel = weatherModelData;
                    }
                });

        unsubscribeOnDestroy(getWeatherByName);
    }

    public void addWeatherToFavorite(WeatherModel weatherModel) {
        Location location = new Location(weatherModel.getId(), weatherModel.getName());
        realmDatabaseManager.addLocation(location);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
