package com.example.maks2.weathertocoordinats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends AppCompatActivity
        implements GoogleMap.OnMapClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    Handler handler;
    LinearLayout buttomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    TextView locationText;
    TextView generalWeatherText;
    TextView temperatureText;
    TextView humidityText;
    TextView pressureText;
    TextView windText;
    ImageView weatherImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        buttomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(buttomSheet);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationText=(TextView) findViewById(R.id.weatherLocation);
        generalWeatherText=(TextView) findViewById(R.id.weatherGeneral);
        temperatureText=(TextView)findViewById(R.id.temperatureView);
        humidityText=(TextView)findViewById(R.id.humidityview);
        pressureText=(TextView)findViewById(R.id.pressureview);
        windText=(TextView)findViewById(R.id.windview);
        weatherImage=(ImageView)findViewById(R.id.iconWeather);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String[] coordinats = getCoordinats(latLng);
        getWeather(coordinats[0], coordinats[1]);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
    }


    public String[] getCoordinats(LatLng latLng) {
        String lat = new String();
        String lng = new String();
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

        MyApplication.getApi().getData(lat, lng, getString(R.string.appid))
                .enqueue(new retrofit2.Callback<WeatherModel>() {
            @Override
            public void onResponse(retrofit2.Call<WeatherModel> call, retrofit2.Response<WeatherModel> response) {
                WeatherModel weatherModel = new WeatherModel();

                if (response.body() == null) {
                    Log.e("PARSE ERROR", "RESPONSE IS EMPTY");
                } else
                    weatherModel = response.body();
                formateResult(weatherModel);
                mMap.clear();
            }

            @Override
            public void onFailure(retrofit2.Call<WeatherModel> call, Throwable t) {
                Log.e("CONNECTION ERROR ", "REQUEST IS FAIL ", t);

            }
        });
    }

    public String formateResult(WeatherModel weatherModel) {
        String result = new String();
        handler = new Handler(Looper.getMainLooper());
        double temp, pressure, humidity, windspeed, winddeg;
        Log.e(" ", weatherModel.getName());

        Toast toast=Toast.makeText(this,getString(R.string.ServiceUnreachable),Toast.LENGTH_SHORT);
        if (weatherModel == null) toast.show();
        else {
            if (weatherModel.getSys().getCountry() == null) {
                locationText.setText(getString(R.string.WhereIsIt) + " "
                        + getString(R.string.UnknownCoordinats));
            }
            else {
                locationText.setText(getString(R.string.WhereIsIt) + " "+
                        weatherModel.getName()+", "
                        + weatherModel.getSys().getCountry());
            }

            Picasso picasso=new Picasso.Builder(this).build();
            if(weatherModel.getWeather().get(0).getIcon().isEmpty()){
                picasso.load(R.drawable.weather_none_available).into(weatherImage);
            }else {
                String icon =weatherModel.getWeather().get(0).getIcon();
                switch (icon){
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
            temp = new Double(weatherModel.getMain().getTemp());
            pressure = new Double(weatherModel.getMain().getPressure());
            humidity = new Double(weatherModel.getMain().getHumidity());
            windspeed = new Double(weatherModel.getWind().getSpeed());
            winddeg = new Double(weatherModel.getWind().getDeg());
            temp = (temp - 273);

            temperatureText.setText(getString(R.string.Temperature) + Math.round(temp) + " C" + "\n");
            pressure = pressure * 0.75006375541921;
            pressureText.setText(getString(R.string.pressure) +" "+ Math.round(pressure) + " Millimeters of mercury");

            humidityText.setText(getString(R.string.humidity) +" "+ humidity + "%" + "\n");
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

