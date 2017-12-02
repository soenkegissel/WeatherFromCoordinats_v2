package com.example.maks2.weathertocoordinats.presenters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.managers.NetworkManager;
import com.example.maks2.weathertocoordinats.models.Example;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.view_interfaces.iFavoritesActivityView;

import java.util.List;

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

    public FavoritesActivityPresenter(Context context) {
        this.context = context;
    }

    public void getWeatherCeveralCities(String id) {
        Subscription subscription = NetworkManager.getWeatherForCeveralCities(id, context.getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Example>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showWeather(weatherModelList);
                        Log.e("WTF ",weatherModelList.size()+"");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showMessage(e.getLocalizedMessage());
                        Log.e("WTF ",e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Example weatherModels) {
                        weatherModelList = weatherModels.getList();
                        Log.e("WTF ",weatherModels.getCnt().toString());
                    }
                });
        unsubscribeOnDestroy(subscription);
    }

}
