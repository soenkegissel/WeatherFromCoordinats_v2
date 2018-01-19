package com.example.maks2.weathertocoordinats;

import android.app.Application;

import com.example.maks2.weathertocoordinats.di.component.AppComponent;
import com.example.maks2.weathertocoordinats.di.component.DaggerAppComponent;
import com.example.maks2.weathertocoordinats.di.module.NetworkModule;
import com.example.maks2.weathertocoordinats.di.module.RepositoryModule;

/**
 * Sorry for this code from Railian Maksym (10.07.17).
 */

public class MyApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
       appComponent = DaggerAppComponent.builder()
               .networkModule(new NetworkModule())
               .repositoryModule(new RepositoryModule(this))
               .build();

    }

   public AppComponent getAppComponent(){
        return appComponent;
   }

}
