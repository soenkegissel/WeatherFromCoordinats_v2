package com.example.maks2.weathertocoordinats.ui.activities;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.BottomSheetBehavior;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.managers.NetworkManager;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MapsActivity extends AppCompatActivity
        implements GoogleMap.OnMapClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private Handler handler;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView locationText;
    private TextView generalWeatherText;
    private TextView temperatureText;
    private TextView humidityText;
    private TextView pressureText;
    private TextView windText;
    private ImageView weatherImage;
    private WeatherModel weatherModel;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        LinearLayout bottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationText = (TextView) findViewById(R.id.weatherLocation);
        generalWeatherText = (TextView) findViewById(R.id.weatherGeneral);
        temperatureText = (TextView) findViewById(R.id.temperatureView);
        humidityText = (TextView) findViewById(R.id.humidityview);
        pressureText = (TextView) findViewById(R.id.pressureview);
        windText = (TextView) findViewById(R.id.windview);
        weatherImage = (ImageView) findViewById(R.id.iconWeather);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(view -> fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp)));

    }

    @Override
    public void onMapClick(LatLng latLng) {
        String[] coordinates = getCoordinates(latLng);
        getWeather(coordinates[0], coordinates[1]);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
    }


    public String[] getCoordinates(LatLng latLng) {
        String lat = "";
        String lng = "";
        char chack;
        final List<WeatherModel> weatherModels = new ArrayList<>();

        int i = 0;
        String coord = latLng.toString();
        char symbols[] = new char[coord.length()];
        final String weather = "o";
        String latlng[] = new String[2];
        latlng = coord.split(",");
        latlng[0] = latlng[0].substring(10);
        latlng[1] = latlng[1].substring(0, latlng[1].length() - 1);

        return latlng;
    }

    public void getWeather(String lat, String lng) {
        NetworkManager.getWeather(lat, lng, getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {
                        formatResult(weatherModel);
                        mMap.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MapsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeatherModel weatherModelData) {
                        weatherModel = weatherModelData;
                    }
                });
    }

    public String formatResult(WeatherModel weatherModel) {
        String result = "";
        handler = new Handler(Looper.getMainLooper());
        double temp, pressure, humidity, windspeed, winddeg;
        Log.e(" ", weatherModel.getName());

        Toast toast = Toast.makeText(this, getString(R.string.ServiceUnreachable), Toast.LENGTH_SHORT);
        if (weatherModel == null) toast.show();
        else {
            if (weatherModel.getSys().getCountry() == null) {
                locationText.setText(getString(R.string.WhereIsIt) + " "
                        + getString(R.string.UnknownCoordinats));
            } else {
                locationText.setText(getString(R.string.WhereIsIt) + " " +
                        weatherModel.getName() + ", "
                        + weatherModel.getSys().getCountry());
            }

            Picasso picasso = new Picasso.Builder(this).build();
            if (weatherModel.getWeather().get(0).getIcon().isEmpty()) {
                picasso.load(R.drawable.weather_none_available).into(weatherImage);
            } else {
                String icon = weatherModel.getWeather().get(0).getIcon();
                switch (icon) {
                    case "01d":
                        picasso.load(R.drawable.weather_clear).into(weatherImage);
                        break;
                    case "03d":
                        picasso.load(R.drawable.weather_few_clouds).into(weatherImage);
                        break;
                    case "04d":
                        picasso.load(R.drawable.weather_clouds).into(weatherImage);
                        break;
                    case "09d":
                        picasso.load(R.drawable.weather_snow_rain).into(weatherImage);
                        break;
                    case "10d":

                        picasso.load(R.drawable.weather_rain_day).into(weatherImage);
                        break;
                    case "11d":
                        picasso.load(R.drawable.weather_storm).into(weatherImage);
                        break;
                    case "13d":
                        picasso.load(R.drawable.weather_snow).into(weatherImage);
                        break;
                    case "50d":
                        picasso.load(R.drawable.weather_mist).into(weatherImage);
                        break;

                }
            }

            generalWeatherText.setText(getString(R.string.Weather) + weatherModel.getWeather().get(0).getDescription());
            temp = weatherModel.getMain().getTemp();
            pressure = weatherModel.getMain().getPressure();
            humidity = weatherModel.getMain().getHumidity();
            windspeed = weatherModel.getWind().getSpeed();
            winddeg = weatherModel.getWind().getDeg();
            temp = (temp - 273);

            temperatureText.setText(getString(R.string.Temperature) + Math.round(temp) + " C" + "\n");
            pressure = pressure * 0.75006375541921;
            pressureText.setText(getString(R.string.pressure) + " " + Math.round(pressure) + " Millimeters of mercury");

            humidityText.setText(getString(R.string.humidity) + " " + humidity + "%" + "\n");
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
            windText.setText(getString(R.string.Wind) + wind + " " + Math.round(windspeed) + " m/s" + "\n");

        }

        return result;
    }
}

