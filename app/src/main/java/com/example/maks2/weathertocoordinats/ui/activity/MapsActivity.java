package com.example.maks2.weathertocoordinats.ui.activity;

import android.support.design.widget.BottomSheetBehavior;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.managers.FragmentsManager;
import com.example.maks2.weathertocoordinats.managers.SharedPreferencesManager;
import com.example.maks2.weathertocoordinats.managers.WeatherManager;
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
        implements GoogleMap.OnMapClickListener, OnMapReadyCallback, SearchView.OnQueryTextListener {

    private GoogleMap mMap;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton fab;
    private FragmentsManager fragmentsManager;
    private WeatherModel weatherModel;
    private SharedPreferencesManager sharedPreferencesManager;
    private WeatherManager weatherManager;
    private SearchView searchView;

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
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        weatherManager=new WeatherManager(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            Bundle args = new Bundle();
            args.putSerializable("WeatherModel", weatherManager
                    .getWeatherByCoordinates(getCoordinates(latLng).get(0),getCoordinates(latLng).get(1)));
            WeatherFragment weatherFragment = new WeatherFragment();
            weatherFragment.setArguments(args);
            fragmentsManager.replaceFragment(weatherFragment);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_activity_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        if (query.length()!=0) {
            WeatherModel weatherModel= weatherManager.getWeatherByName(query);
            if(weatherModel!=null) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                Bundle args = new Bundle();
                args.putSerializable("WeatherModel", weatherManager.getWeatherByName(query));
                WeatherFragment weatherFragment = new WeatherFragment();
                weatherFragment.setArguments(args);
                fragmentsManager.replaceFragment(weatherFragment);
            }
            return true;
        }else {
            Toast toast=Toast.makeText(this,"Please input your search response",Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.e("",newText);
        return true;
    }
}

