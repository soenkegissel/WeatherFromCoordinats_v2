package com.rucksack.weathermaps.managers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;



public class SharedPreferencesManager {

    private static final String NAME_KEY = "pref";
    private static final String DEF_STRING_VALUE = "";
    private static final Long DEF_LONG_VALUE = 0L;


    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        mContext = context;
        initPref();
    }

    private void initPref() {
        initShareAndEdit(NAME_KEY);
    }

    private void initShareAndEdit(String name) {
        sharedPreferences = mContext.getSharedPreferences(name, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, DEF_STRING_VALUE);
    }

    public void putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public Long getLong(String key) {
        return sharedPreferences.getLong(key, DEF_LONG_VALUE);
    }

    public void putListString(String key, List<String> value) {
        editor.putStringSet(key, new HashSet<>(value));
        editor.apply();
    }

    public List<String> getListString(String key) {
        List<String> value = new ArrayList<>();
        value.addAll(sharedPreferences.getStringSet(key, new HashSet<>()));
        return value;
    }

}
