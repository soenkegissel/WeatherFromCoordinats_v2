package com.rucksack.weathermaps.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.rucksack.weathermaps.Constants;
import com.rucksack.weathermaps.MyApplication;
import com.rucksack.weathermaps.R;
import com.rucksack.weathermaps.managers.HttpErrorViewManager;
import com.rucksack.weathermaps.managers.NetworkManager;
import com.rucksack.weathermaps.managers.RealmDatabaseManager;
import com.rucksack.weathermaps.models.Location;
import com.rucksack.weathermaps.models.WeatherModel;
import com.rucksack.weathermaps.view_interfaces.iFavoritesActivityView;

import io.reactivex.disposables.Disposable;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;


@InjectViewState
public class FavoritesActivityPresenter extends BasePresenter<iFavoritesActivityView> {
    private List<WeatherModel> weatherModelList;
    private Context context;
    @Inject
    Realm realm;
    @Inject
    RealmDatabaseManager realmDatabaseManager;
    @Inject
    NetworkManager networkManager;

    public FavoritesActivityPresenter(Context context) {
        this.context = context;
        ((MyApplication) context.getApplicationContext()).getAppComponent().inject(this);
    }

    public void getWeatherCeveralCities(String id, String units) {
        Disposable subscription = networkManager.getWeatherForCeveralCities(id, units, Constants.APP_ID)
                .compose(NetworkManager.applyObservableAsync())
                .filter(example -> example != null)
                .subscribe(example -> weatherModelList = example.getList(),
                        throwable -> getViewState().showMessage(HttpErrorViewManager.convertToText(context, throwable.getLocalizedMessage())),
                        () -> getViewState().showWeather(weatherModelList));

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
