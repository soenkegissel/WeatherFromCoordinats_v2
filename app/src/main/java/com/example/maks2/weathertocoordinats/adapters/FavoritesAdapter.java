package com.example.maks2.weathertocoordinats.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.view_interfaces.iFavoritesActivityView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Sorry for this code from Railian Maksym (21.11.2017).
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private Context context;
    private List<WeatherModel> weatherModelList = new ArrayList<>();

    public FavoritesAdapter(Context context, List<WeatherModel> weatherModelList) {
        this.context = context;
        if(weatherModelList!=null) this.weatherModelList = weatherModelList;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoritesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_card, parent, false));
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        holder.showWeather(weatherModelList.get(position));
        holder.imageView_refresh.setOnClickListener(view -> {
            Toast toast = Toast.makeText(context, "CLICK", Toast.LENGTH_LONG);
            toast.show();
        });


    }

    @Override
    public int getItemCount() {
        return weatherModelList.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.imageView_refresh)
        ImageView imageView_refresh;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        boolean checkForWord(String line, String word) {
            return line.contains(word);
        }

        public void showWeather(WeatherModel weatherModel) {
            double temp, windspeed, winddeg;
            Log.e(" ", weatherModel.getName());

            Toast toast = Toast.makeText(context, context.getString(R.string.ServiceUnreachable), Toast.LENGTH_SHORT);
            if (weatherModel == null) toast.show();
            else {
                if (weatherModel.getSys().getCountry() == null) {
                    locationText.setText(context.getString(R.string.UnknownCoordinats));
                } else {
                    locationText.setText(weatherModel.getName() + ", "
                            + weatherModel.getSys().getCountry());
                }

                Picasso picasso = new Picasso.Builder(context).build();
                if (weatherModel.getWeather().get(0).getIcon().isEmpty()) {
                    picasso.load(R.drawable.weather_none_available).into(weatherImage);
                } else {
                    if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "clear")) {
                        picasso.load(R.drawable.weather_clear).into(weatherImage);
                        picasso.load(R.drawable.ic_wind_white).into(windicon);
                        picasso.load(R.drawable.ic_action_refresh_white).into(imageView_refresh);
                        decorateWeatherCard(R.color.sunnyBackground, R.color.white);
                    } else if (checkForWord(weatherModel.getWeather().get(0).getDescription(), "few")) {
                        picasso.load(R.drawable.weather_few_clouds).into(weatherImage);
                        picasso.load(R.drawable.ic_wind_white).into(windicon);
                        picasso.load(R.drawable.ic_action_refresh_white).into(imageView_refresh);
                        decorateWeatherCard(R.color.sunnyBackground, R.color.white);
                    } else if (!checkForWord(weatherModel.getWeather().get(0).getDescription(), "few")
                            && checkForWord(weatherModel.getWeather().get(0).getDescription(), "clouds")) {
                        picasso.load(R.drawable.weather_clouds).into(weatherImage);
                        picasso.load(R.drawable.ic_wind_white).into(windicon);
                        picasso.load(R.drawable.ic_action_refresh_white).into(imageView_refresh);
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
                        picasso.load(R.drawable.ic_action_refresh_white).into(imageView_refresh);
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


                generalWeatherText.setText(weatherModel.getWeather().get(0).getDescription());
                temp = weatherModel.getMain().getTemp();
                windspeed = weatherModel.getWind().getSpeed();
                winddeg = weatherModel.getWind().getDeg();
                temp = (temp - 273);

                temperatureText.setText(Math.round(temp) + " °C" + "\n");
                String wind;
                if (winddeg <= 20 && winddeg >= 340) wind = context.getString(R.string.Nord);
                else if (winddeg <= 80 && winddeg >= 21)
                    wind = context.getString(R.string.NordEast);
                else if (winddeg <= 100 && winddeg >= 81) wind = context.getString(R.string.East);
                else if (winddeg >= 101 && winddeg <= 170)
                    wind = context.getString(R.string.SouthEast);
                else if (winddeg <= 190 && winddeg >= 171) wind = context.getString(R.string.South);
                else if (winddeg <= 260 && winddeg >= 191)
                    wind = context.getString(R.string.SouthWest);
                else if (winddeg >= 261 && winddeg <= 280) wind = context.getString(R.string.West);
                else wind = context.getString(R.string.NordWest);
                windText.setText(wind + " " + Math.round(windspeed) + " m/s" + "\n");
            }
        }

        private void decorateWeatherCard(int colorBack, int colorFont) {
            weatherCard.setBackgroundColor(context.getResources().getColor(colorBack));
            generalWeatherText.setTextColor(context.getResources().getColor(colorFont));
            locationText.setTextColor(context.getResources().getColor(colorFont));
            temperatureText.setTextColor(context.getResources().getColor(colorFont));
            windText.setTextColor(context.getResources().getColor(colorFont));
            imageView_refresh.setVisibility(View.VISIBLE);
        }


    }
}
