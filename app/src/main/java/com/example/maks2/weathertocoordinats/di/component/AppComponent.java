package com.example.maks2.weathertocoordinats.di.component;

import com.example.maks2.weathertocoordinats.adapters.FavoritesAdapter;
import com.example.maks2.weathertocoordinats.di.module.NetworkModule;
import com.example.maks2.weathertocoordinats.di.module.RepositoryModule;
import com.example.maks2.weathertocoordinats.managers.SharedPreferencesManager;
import com.example.maks2.weathertocoordinats.presenters.FavoritesActivityPresenter;
import com.example.maks2.weathertocoordinats.presenters.MapsActivityPresenter;
import com.example.maks2.weathertocoordinats.ui.activity.FavoritesActivity;
import com.example.maks2.weathertocoordinats.ui.activity.MapsActivity;
import com.example.maks2.weathertocoordinats.ui.activity.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(MapsActivityPresenter mapsActivityPresenter);
    void inject(FavoritesActivityPresenter favoritesActivityPresenter);
    void inject(FavoritesAdapter favoritesAdapter);
    void inject(FavoritesActivity favoritesActivity);
    void inject(MapsActivity mapsActivity);
    void inject(SettingsActivity settingsActivity);
}
