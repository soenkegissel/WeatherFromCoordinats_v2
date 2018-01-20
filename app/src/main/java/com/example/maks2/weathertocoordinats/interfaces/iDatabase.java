package com.example.maks2.weathertocoordinats.interfaces;

import com.example.maks2.weathertocoordinats.models.Location;

import java.util.List;


public interface iDatabase {
    void addLocation(Location location);
    Location getLocationbyID(int id);
    List<Location>getLocations();
    void removeLocation(Location location);
    void removeAllLocations();
    void removeLocationsList(List<Location> locations);
}
