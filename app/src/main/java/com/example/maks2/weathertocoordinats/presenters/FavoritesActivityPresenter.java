package com.example.maks2.weathertocoordinats.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.example.maks2.weathertocoordinats.Constants;
import com.example.maks2.weathertocoordinats.MyApplication;
import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.managers.HttpErrorViewManager;
import com.example.maks2.weathertocoordinats.managers.NetworkManager;
import com.example.maks2.weathertocoordinats.managers.RealmDatabaseManager;
import com.example.maks2.weathertocoordinats.models.Example;
import com.example.maks2.weathertocoordinats.models.Location;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.view_interfaces.iFavoritesActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DefaultSubscriber;

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
