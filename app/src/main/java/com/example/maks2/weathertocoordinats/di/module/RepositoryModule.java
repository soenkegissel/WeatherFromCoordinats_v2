package com.example.maks2.weathertocoordinats.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.maks2.weathertocoordinats.managers.RealmDatabaseManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by raili on 19.01.2018.
 */
@Module
public class RepositoryModule {
Application application;
    public RepositoryModule(Application application ) {
        this.application=application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Realm providesRealm(Application application){
        Realm.init(application.getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("favorites.realm").build();
        Realm.setDefaultConfiguration(config);
        return  Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    RealmDatabaseManager providesRealmDatabaseManager(Realm realm){
        return new RealmDatabaseManager(realm);
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }


}
