package com.example.maks2.weathertocoordinats.di.component;

import com.example.maks2.weathertocoordinats.di.module.NetworkModule;
import com.example.maks2.weathertocoordinats.di.module.RepositoryModule;
import com.example.maks2.weathertocoordinats.presenters.FavoritesActivityPresenter;
import com.example.maks2.weathertocoordinats.presenters.MapsActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by raili on 19.01.2018.
 */
@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(MapsActivityPresenter mapsActivityPresenter);
    void inject(FavoritesActivityPresenter favoritesActivityPresenter);
}
