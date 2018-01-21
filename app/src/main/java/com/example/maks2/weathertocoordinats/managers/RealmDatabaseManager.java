package com.example.maks2.weathertocoordinats.managers;

import com.example.maks2.weathertocoordinats.interfaces.iDatabase;
import com.example.maks2.weathertocoordinats.models.Location;

import java.util.List;

import io.realm.Realm;


public class RealmDatabaseManager implements iDatabase {
    private Realm realm;

    public RealmDatabaseManager(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void addLocation(Location location) {
        realm.beginTransaction();
        realm.insertOrUpdate(location);
        realm.commitTransaction();
    }

    @Override
    public Location getLocationbyID(int id) {
        realm.beginTransaction();
        Location location = realm.where(Location.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return location;
    }

    @Override
    public List<Location> getLocations() {
        realm.beginTransaction();
        List<Location> locations = realm.where(Location.class).findAll();
        realm.commitTransaction();
        return locations;
    }

    @Override
    public void removeLocation(Location location) {
        realm.beginTransaction();
        location.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void removeAllLocations() {
        realm.beginTransaction();
        List<Location> locations = realm.where(Location.class).findAll();
        for (Location l : locations) {
            l.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public void removeLocationsList(List<Location> locations) {
        realm.beginTransaction();
        for (Location l : locations) {
            l.deleteFromRealm();
        }
        realm.commitTransaction();
    }
}
