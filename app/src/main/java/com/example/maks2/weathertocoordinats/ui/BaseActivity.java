package com.example.maks2.weathertocoordinats.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.MvpAppCompatActivity;

import rx.subscriptions.CompositeSubscription;

/**
 * Sorry for this code from Railian Maksym (14.11.2017).
 */

public class BaseActivity extends MvpAppCompatActivity {
    private CompositeSubscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = new CompositeSubscription();
    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
        super.onDestroy();
    }

    public CompositeSubscription getSubscription() {
        return mSubscription;
    }

    public static String convertUnits(String units){
        if (units.contains("Celsius")){
            return "metric";
        }else if (units.contains("Fahrenheit")){
            return "imperial";
        }else return new String();
    }

}
