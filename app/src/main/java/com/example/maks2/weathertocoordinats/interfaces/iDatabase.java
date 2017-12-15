package com.example.maks2.weathertocoordinats.interfaces;

import com.example.maks2.weathertocoordinats.models.Location;

import java.util.List;

/**
 * Sorry for this code from Railian Maksym (02.12.2017).
 */

public interface iDatabase {
    void addLocation(Location location);
    void addLocations(List<Location> locations);
    Location getLocationbyID(int id);
    List<Location>getLocations();
    void removeLocation(Location location);
    void removeAllLocations();
    void removeLocationsList(List<Location> locations);
}
