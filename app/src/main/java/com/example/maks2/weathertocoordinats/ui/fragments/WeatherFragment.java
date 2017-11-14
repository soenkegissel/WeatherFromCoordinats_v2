package com.example.maks2.weathertocoordinats.ui.fragments;

import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.managers.NetworkManager;
import com.example.maks2.weathertocoordinats.managers.SharedPreferencesManager;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.ui.activity.MapsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Sorry for this code from Railian Maksym (14.11.2017).
 */

public class WeatherFragment extends Fragment {

    private Handler handler;
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
    ImageView windicon;
    ConstraintLayout weatherCard;
    private WeatherModel weatherModel;
    private SharedPreferencesManager sharedPreferencesManager;
    private List<String> latlng;

    public static WeatherFragment newInstance(WeatherModel weatherModels) {
        WeatherFragment weatherFragment = new WeatherFragment();
        return weatherFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        latlng = sharedPreferencesManager.getListString("latlng");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_card, container, false);
        generalWeatherText = view.findViewById(R.id.generalWeatherText);
        locationText = view.findViewById(R.id.locationText);
        temperatureText = view.findViewById(R.id.temperatureText);
        windText = view.findViewById(R.id.windText);
        weatherImage = view.findViewById(R.id.weatherImage);
        weatherCard = view.findViewById(R.id.weather_layout);
        windicon = view.findViewById(R.id.windicon);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!latlng.isEmpty()) {
            getWeather(latlng.get(0), latlng.get(1));
        }
    }


    public void initWeather(WeatherModel weatherModel) {
        double temp, windspeed, winddeg;
        Log.e(" ", weatherModel.getName());

        Toast toast = Toast.makeText(getActivity(), getString(R.string.ServiceUnreachable), Toast.LENGTH_SHORT);
        if (weatherModel == null) toast.show();
        else {
            if (weatherModel.getSys().getCountry() == null) {
                locationText.setText(getString(R.string.UnknownCoordinats));
            } else {
                locationText.setText(weatherModel.getName() + ", "
                        + weatherModel.getSys().getCountry());
            }

            Picasso picasso = new Picasso.Builder(getActivity()).build();
            if (weatherModel.getWeather().get(0).getIcon().isEmpty()) {
                picasso.load(R.drawable.weather_none_available).into(weatherImage);
            } else {
                if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "clear")) {
                    picasso.load(R.drawable.weather_clear).into(weatherImage);
                    picasso.load(R.drawable.ic_action_name).into(windicon);
                    decorateWeatherCard(R.color.sunnyBackground, R.color.white);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "few")) {
                    picasso.load(R.drawable.weather_few_clouds).into(weatherImage);
                    picasso.load(R.drawable.ic_action_name).into(windicon);
                    decorateWeatherCard(R.color.sunnyBackground, R.color.white);
                } else if (!checkForWord(weatherModel.getWeather().get(0).getDescription(), "few")
                        && checkForWord(weatherModel.getWeather().get(0).getDescription(), "clouds")) {
                    picasso.load(R.drawable.weather_clouds).into(weatherImage);
                    picasso.load(R.drawable.ic_action_name).into(windicon);
                    decorateWeatherCard(R.color.rainBackground, R.color.white);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "rain")
                        & checkForWord(weatherModel.getWeather().get(0).getDescription(), "snow")) {
                    picasso.load(R.drawable.weather_snow_rain).into(weatherImage);
                    picasso.load(R.drawable.ic_wind_black).into(windicon);
                    decorateWeatherCard(R.color.snowBackground, R.color.primary_text);
                } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "rain")
                        && !checkForWord(weatherModel.getWeather().get(0).getDescription(), "snow")) {
                    picasso.load(R.drawable.weather_rain_day).into(weatherImage);
                    picasso.load(R.drawable.ic_action_name).into(windicon);
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
        temp = (temp - 273);

        temperatureText.setText(Math.round(temp) + " °C" + "\n");
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
    }
    
    public void getWeather(String lat, String lng) {
        NetworkManager.getWeather(lat, lng, getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {
                        initWeather(weatherModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeatherModel weatherModelData) {
                        weatherModel = weatherModelData;
                    }
                });
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
}
