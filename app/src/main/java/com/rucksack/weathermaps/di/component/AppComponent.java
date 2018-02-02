package com.rucksack.weathermaps.di.component;

import com.rucksack.weathermaps.adapters.FavoritesAdapter;
import com.rucksack.weathermaps.di.module.NetworkModule;
import com.rucksack.weathermaps.di.module.RepositoryModule;
import com.rucksack.weathermaps.presenters.FavoritesActivityPresenter;
import com.rucksack.weathermaps.presenters.MapsActivityPresenter;
import com.rucksack.weathermaps.ui.activity.FavoritesActivity;
import com.rucksack.weathermaps.ui.activity.MapsActivity;
import com.rucksack.weathermaps.ui.activity.SettingsActivity;

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
