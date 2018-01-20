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

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

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
        ((MyApplication)context.getApplicationContext()).getAppComponent().inject(this);
    }

    public void getWeatherCeveralCities(String id, String units) {
        Subscription subscription = networkManager.getWeatherForCeveralCities(id, units, Constants.APP_ID)
                .compose(NetworkManager.addObservableParameters())
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
