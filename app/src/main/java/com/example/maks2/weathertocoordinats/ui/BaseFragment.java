package com.example.maks2.weathertocoordinats.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import rx.subscriptions.CompositeSubscription;

/**
 * Sorry for this code from Railian Maksym (15.11.2017).
 */

public class BaseFragment extends Fragment {
    private CompositeSubscription mSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
        super.onDestroy();
    }

    public CompositeSubscription getSubscription() {
        return mSubscription;
    }

}
