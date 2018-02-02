package com.rucksack.weathermaps.interfaces;

import com.rucksack.weathermaps.models.Location;

import java.util.List;


public interface iDatabase {
    void addLocation(Location location);
    Location getLocationbyID(int id);
    List<Location>getLocations();
    void removeLocation(Location location);
    void removeAllLocations();
    void removeLocationsList(List<Location> locations);
}
