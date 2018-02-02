package com.rucksack.weathermaps.models;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Sorry for this code from Railian Maksym (02.12.2017).
 */

public class Location extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;

    public Location(){}

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static String listToString(List<Location> locations) {
        String output=new String();
        for(Location location:locations){
            if(location.getId()!=locations.get(locations.size()-1).getId()){
                output+=location.getId()+",";
            }else output+=location.getId();
        }
        return output;
    }
}
