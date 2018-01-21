package com.example.maks2.weathertocoordinats.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.arellomobile.mvp.MvpAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Sorry for this code from Railian Maksym (14.11.2017).
 */

public class BaseActivity extends MvpAppCompatActivity {
    private CompositeDisposable mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null)
            mSubscription.dispose();
        super.onDestroy();
    }

    public CompositeDisposable getSubscription() {
        return mSubscription;
    }

    public static String convertUnits(String units){
        if (units.contains("Celsius")){
            return "metric";
        }else if (units.contains("Fahrenheit")){
            return "imperial";
        }else return "";
    }

}
