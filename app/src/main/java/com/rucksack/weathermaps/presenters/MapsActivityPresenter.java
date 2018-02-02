package com.rucksack.weathermaps.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.rucksack.weathermaps.Constants;
import com.rucksack.weathermaps.MyApplication;
import com.rucksack.weathermaps.managers.HttpErrorViewManager;
import com.rucksack.weathermaps.managers.NetworkManager;
import com.rucksack.weathermaps.managers.RealmDatabaseManager;
import com.rucksack.weathermaps.models.Location;
import com.rucksack.weathermaps.models.WeatherModel;
import com.rucksack.weathermaps.view_interfaces.iMapsActivityView;

import io.reactivex.disposables.Disposable;

import javax.inject.Inject;

import io.realm.Realm;


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
        ((MyApplication) context.getApplicationContext()).getAppComponent().inject(this);
    }

    public void getWeatherByCoordinates(String lat, String lng, String units) {
        Disposable getWeatherByCoordinates = networkManager.getWeather(lat, lng, units, Constants.APP_ID)
                .compose(NetworkManager.applyObservableAsync())
                .filter(weatherModelData -> weatherModelData.getWind().getDeg() != null)
                .subscribe(weatherModelData -> weatherModel = weatherModelData,
                        throwable -> getViewState().showMessage(HttpErrorViewManager.convertToText(context, throwable.getLocalizedMessage())),
                        () -> getViewState().showWeather(weatherModel));
        unsubscribeOnDestroy(getWeatherByCoordinates);
    }

    public void getWeatherByName(String name, String units) {

        Disposable getWeatherByName = networkManager
                .getWeatherByCityName(name, units, Constants.APP_ID)
                .compose(NetworkManager.applyObservableAsync())
                .filter(weatherModelData -> weatherModelData.getWind().getDeg() != null)
                .subscribe(weatherModelData -> weatherModel = weatherModelData,
                        throwable -> getViewState().showMessage(HttpErrorViewManager.convertToText(context, throwable.getLocalizedMessage())),
                        () -> getViewState().showWeather(weatherModel));
        unsubscribeOnDestroy(getWeatherByName);
    }

    public void addWeatherToFavorite(WeatherModel weatherModel) {
        Location location = new Location(weatherModel.getId(), weatherModel.getName());
        realmDatabaseManager.addLocation(location);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //realm.close();
    }
}
