package com.example.maks2.weathertocoordinats.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.adapters.FavoritesAdapter;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.presenters.FavoritesActivityPresenter;
import com.example.maks2.weathertocoordinats.presenters.MapsActivityPresenter;
import com.example.maks2.weathertocoordinats.ui.BaseActivity;
import com.example.maks2.weathertocoordinats.view_interfaces.iFavoritesActivityView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Sorry for this code from Railian Maksym (21.11.2017).
 */

public class FavoritesActivity extends BaseActivity implements iFavoritesActivityView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.favorites_RV)
    RecyclerView favorites_RV;

    @InjectPresenter
    FavoritesActivityPresenter presenter;
    FavoritesAdapter favoritesAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layoutManager = new LinearLayoutManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getWeatherCeveralCities("524901,703448,2643743");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorites_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showWeather(List<WeatherModel> weatherModels) {
        favoritesAdapter = new FavoritesAdapter(this, weatherModels);
        favorites_RV.setLayoutManager(layoutManager);
        favorites_RV.setAdapter(favoritesAdapter);
    }

    @Override
    public void refreshWeather(int position, WeatherModel weatherModel) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {
       Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @ProvidePresenter
    FavoritesActivityPresenter providePresenter() {
        return new FavoritesActivityPresenter(this);
    }
}
