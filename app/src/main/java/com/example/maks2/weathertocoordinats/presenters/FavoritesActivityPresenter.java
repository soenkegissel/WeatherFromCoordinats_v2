package com.example.maks2.weathertocoordinats.presenters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.managers.HttpErrorViewManager;
import com.example.maks2.weathertocoordinats.managers.NetworkManager;
import com.example.maks2.weathertocoordinats.managers.RealmDatabaseManager;
import com.example.maks2.weathertocoordinats.models.Example;
import com.example.maks2.weathertocoordinats.models.Location;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.view_interfaces.iFavoritesActivityView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Sorry for this code from Railian Maksym (21.11.2017).
 */
@InjectViewState
public class FavoritesActivityPresenter extends BasePresenter<iFavoritesActivityView> {
    Context context;
    private List<WeatherModel> weatherModelList;
    Realm realm;
    RealmDatabaseManager realmDatabaseManager;

    public FavoritesActivityPresenter(Context context) {
        this.context = context;
        realm = Realm.getDefaultInstance();
        realmDatabaseManager = new RealmDatabaseManager(realm);
    }

    public void getWeatherCeveralCities(String id, String units) {
        Subscription subscription = NetworkManager.getWeatherForCeveralCities(id, units, context.getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Example>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showWeather(weatherModelList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showMessage(HttpErrorViewManager.convertToText(context,e.getLocalizedMessage()));
                    }

                    @Override
                    public void onNext(Example weatherModels) {
                        weatherModelList = weatherModels.getList();
                    }
                });
        unsubscribeOnDestroy(subscription);
    }

    public Location getLocationbyID(int id) {
        return realmDatabaseManager.getLocationbyID(id);
    }

    public List<Location> getLocations() {
        return realmDatabaseManager.getLocations();
    }

    public void removeLocation(Location location) {
        realmDatabaseManager.removeLocation(location);
    }

    public void removeAllLocations() {
        realmDatabaseManager.removeAllLocations();
        getViewState().showMessage(context.getString(R.string.locations_cleared_successfully));
    }

    public void removeLocationsList(List<Location> locations) {
        realmDatabaseManager.removeLocationsList(locations);
    }

}
