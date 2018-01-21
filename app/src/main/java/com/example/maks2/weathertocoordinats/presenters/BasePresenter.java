package com.example.maks2.weathertocoordinats.presenters;

import android.support.annotation.NonNull;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;



public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {
    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    void unsubscribeOnDestroy(@NonNull Disposable subscription) {
        compositeSubscription.add(subscription);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }
}
