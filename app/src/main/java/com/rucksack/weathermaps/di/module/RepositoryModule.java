package com.rucksack.weathermaps.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rucksack.weathermaps.managers.RealmDatabaseManager;
import com.rucksack.weathermaps.managers.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class RepositoryModule {
    private Application application;

    public RepositoryModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    SharedPreferencesManager providesSharedPreferencesManager(Application application){
        return new SharedPreferencesManager(application.getApplicationContext());
    }

    @Provides
    @Singleton
    Realm providesRealm(Application application) {
        Realm.init(application.getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("favorites.realm").build();
        Realm.setDefaultConfiguration(config);
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    RealmDatabaseManager providesRealmDatabaseManager(Realm realm) {
        return new RealmDatabaseManager(realm);
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }


}
