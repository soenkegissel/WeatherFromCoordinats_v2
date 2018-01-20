package com.example.maks2.weathertocoordinats.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import com.example.maks2.weathertocoordinats.MyApplication;
import com.example.maks2.weathertocoordinats.R;

import com.example.maks2.weathertocoordinats.managers.SharedPreferencesManager;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.presenters.MapsActivityPresenter;
import com.example.maks2.weathertocoordinats.ui.BaseActivity;

import com.example.maks2.weathertocoordinats.view_interfaces.iMapsActivityView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapsActivity extends BaseActivity
        implements GoogleMap.OnMapClickListener, OnMapReadyCallback, SearchView.OnQueryTextListener, iMapsActivityView {

    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton fab;
    private SearchView searchView;
    private WeatherModel weatherModelTemp;
    private String units;

    @InjectPresenter
    MapsActivityPresenter mapsActivityPresenter;

    @BindView(R.id.locationText)
    TextView locationText;
    @BindView(R.id.generalWeatherText)
    TextView generalWeatherText;
    @BindView(R.id.temperatureText)
    TextView temperatureText;
    @BindView(R.id.windText)
    TextView windText;
    @BindView(R.id.weatherImage)
    ImageView weatherImage;
    @BindView(R.id.windicon)
    ImageView windicon;
    @BindView(R.id.weather_layout)
    ConstraintLayout weatherCard;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        ((MyApplication)getApplication()).getAppComponent().inject(this);
        LinearLayout bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_favorite_add));
                    weatherModelTemp = null;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if (weatherModelTemp != null) {
                if (weatherModelTemp.getName().length() != 0) {
                    mapsActivityPresenter.addWeatherToFavorite(weatherModelTemp);
                    showMessage(getString(R.string.added_to_favorite));
                    weatherModelTemp = null;
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_go_to_favorites));

                } else showMessage("You can't add unknown location to favorites");
            } else {
                showMessage("Please select location before add it to favorites");
            }

        });
        sharedPreferencesManager.putListString("latlng", new ArrayList<>());
        Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        mapsActivityPresenter.getWeatherByCoordinates(getCoordinates(latLng).get(0), getCoordinates(latLng).get(1), units);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(this);
    }


    public List<String> getCoordinates(LatLng latLng) {
        String coord = latLng.toString();
        String latlng[];
        List<String> toReturn = new ArrayList<>();
        latlng = coord.split(",");
        toReturn.add(latlng[0].substring(10));
        toReturn.add(latlng[1].substring(0, latlng[1].length() - 1));
        return toReturn;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_activity_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_to_favorite:
                Intent intent = new Intent(MapsActivity.this, FavoritesActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_go_to_settings:
                intent = new Intent(MapsActivity.this, SettingsActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        if (query.length() != 0) {
            if (query.contains(",")) {
                mapsActivityPresenter.getWeatherByName(query, units);
                return true;
            } else showMessage("Please input City and Country in english (London, GB for example)");
            return false;
        } else {
            Toast toast = Toast.makeText(this, "Please input your search response", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.e("", newText);
        return true;
    }

    private void decorateWeatherCard(int colorBack, int colorFont) {
        weatherCard.setBackgroundColor(getResources().getColor(colorBack));
        generalWeatherText.setTextColor(getResources().getColor(colorFont));
        locationText.setTextColor(getResources().getColor(colorFont));
        temperatureText.setTextColor(getResources().getColor(colorFont));
        windText.setTextColor(getResources().getColor(colorFont));
    }

    boolean checkForWord(String line, String word) {
        return line.contains(word);
    }

    @Override
    public void showWeather(WeatherModel weatherModel) {
        weatherModelTemp = weatherModel;
        double temp, windspeed, winddeg;
        Log.e(" ", weatherModel.getName());

        Toast toast = Toast.makeText(this, getString(R.string.ServiceUnreachable), Toast.LENGTH_SHORT);
        if (weatherModel == null) toast.show();
        else {
            if (weatherModel.getSys().getCountry() == null) {
                locationText.setText(getString(R.string.UnknownCoordinats));
            } else {
                locationText.setText(weatherModel.getName() + ", "
                        + weatherModel.getSys().getCountry());
            }

            Picasso picasso = new Picasso.Builder(this).build();
            if (weatherModel.getWeather().get(0).getIcon().isEmpty()) {
                picasso.load(R.drawable.weather_none_available).into(weatherImage);
            } else {
                if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "clear")) {
                    picasso.load(R.drawable.weather_clear).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_white).into(windicon);
                    decorateWeatherCard(R.color.sunnyBackground, R.color.white);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "few")) {
                    picasso.load(R.drawable.weather_few_clouds).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_white).into(windicon);
                    decorateWeatherCard(R.color.sunnyBackground, R.color.white);
                } else if (!checkForWord(weatherModel.getWeather().get(0).getDescription(), "few")
                        && checkForWord(weatherModel.getWeather().get(0).getDescription(), "clouds")) {
                    picasso.load(R.drawable.weather_clouds).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_white).into(windicon);
                    decorateWeatherCard(R.color.rainBackground, R.color.white);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "rain")
                        & checkForWord(weatherModel.getWeather().get(0).getDescription(), "snow")) {
                    picasso.load(R.drawable.weather_snow_rain).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_black).into(windicon);
                    decorateWeatherCard(R.color.snowBackground, R.color.primary_text);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "rain")
                        && !checkForWord(weatherModel.getWeather().get(0).getDescription(), "snow")) {
                    picasso.load(R.drawable.weather_rain_day).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_white).into(windicon);
                    decorateWeatherCard(R.color.rainBackground, R.color.white);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "storm")) {
                    picasso.load(R.drawable.weather_storm).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_black).into(windicon);
                    decorateWeatherCard(R.color.snowBackground, R.color.primary_text);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "snow")
                        & !checkForWord(weatherModel.getWeather().get(0).getDescription(), "rain")) {
                    picasso.load(R.drawable.weather_snow).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_black).into(windicon);
                    decorateWeatherCard(R.color.snowBackground, R.color.primary_text);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "mist")) {
                    picasso.load(R.drawable.weather_mist).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_black).into(windicon);
                    decorateWeatherCard(R.color.snowBackground, R.color.primary_text);
                }

            }
        }

        generalWeatherText.setText(weatherModel.getWeather().get(0).getDescription());
        temp = weatherModel.getMain().getTemp();
        windspeed = weatherModel.getWind().getSpeed();
        winddeg = weatherModel.getWind().getDeg();
        switch (units) {
            case "metric":
                temperatureText.setText(Math.round(temp) + " °C" + "\n");
                break;
            case "imperial":
                temperatureText.setText(Math.round(temp) + " °F" + "\n");
                break;
            default:
                temperatureText.setText(Math.round(temp) + " °K" + "\n");
                break;
        }
        String wind;
        if (winddeg <= 20 && winddeg >= 340) wind = getString(R.string.Nord);
        else if (winddeg <= 80 && winddeg >= 21)
            wind = getString(R.string.NordEast);
        else if (winddeg <= 100 && winddeg >= 81) wind = getString(R.string.East);
        else if (winddeg >= 101 && winddeg <= 170)
            wind = getString(R.string.SouthEast);
        else if (winddeg <= 190 && winddeg >= 171) wind = getString(R.string.South);
        else if (winddeg <= 260 && winddeg >= 191)
            wind = getString(R.string.SouthWest);
        else if (winddeg >= 261 && winddeg <= 280) wind = getString(R.string.West);
        else wind = getString(R.string.NordWest);
        windText.setText(wind + " " + Math.round(windspeed) + " m/s" + "\n");
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @ProvidePresenter
    MapsActivityPresenter providePresenter() {
        return new MapsActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        units = BaseActivity.convertUnits(sharedPreferencesManager.getString("units"));
    }
}

