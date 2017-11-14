package com.example.maks2.weathertocoordinats.ui.activity;

import android.support.design.widget.BottomSheetBehavior;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.LinearLayout;
import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.managers.FragmentsManager;
import com.example.maks2.weathertocoordinats.managers.SharedPreferencesManager;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.ui.BaseActivity;
import com.example.maks2.weathertocoordinats.ui.fragments.WeatherFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;



public class MapsActivity extends BaseActivity
        implements GoogleMap.OnMapClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton fab;
    private FragmentsManager fragmentsManager;
    private WeatherModel weatherModel;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        LinearLayout bottomSheet=findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fragmentsManager=new FragmentsManager(this,R.id.weatherFragment);

        fab= findViewById(R.id.fab);
        fab.setOnClickListener(view -> fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp)));
        sharedPreferencesManager= new SharedPreferencesManager(this);
        sharedPreferencesManager.putListString("latlng",new ArrayList<>());
    }

    @Override
    public void onMapClick(LatLng latLng) {

        List<String> coordinates = getCoordinates(latLng);
        sharedPreferencesManager.putListString("latlng",coordinates);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        fragmentsManager.replaceFragment(WeatherFragment.newInstance(weatherModel));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
    }


    public List<String> getCoordinates(LatLng latLng) {
        String coord = latLng.toString();
        String latlng[];
        List<String> toReturn=new ArrayList<>();
        latlng = coord.split(",");
        toReturn.add(latlng[0].substring(10)) ;
        toReturn.add(latlng[1].substring(0, latlng[1].length() - 1));

        return toReturn;
    }



}

