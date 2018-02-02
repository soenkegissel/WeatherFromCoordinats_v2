package com.rucksack.weathermaps;

import android.app.Application;

import com.rucksack.weathermaps.di.component.AppComponent;
import com.rucksack.weathermaps.di.component.DaggerAppComponent;
import com.rucksack.weathermaps.di.module.NetworkModule;
import com.rucksack.weathermaps.di.module.RepositoryModule;


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
