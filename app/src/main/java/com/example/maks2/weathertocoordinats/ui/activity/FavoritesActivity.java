package com.example.maks2.weathertocoordinats.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.maks2.weathertocoordinats.R;
import com.example.maks2.weathertocoordinats.adapters.FavoritesAdapter;
import com.example.maks2.weathertocoordinats.models.Location;
import com.example.maks2.weathertocoordinats.models.WeatherModel;
import com.example.maks2.weathertocoordinats.presenters.FavoritesActivityPresenter;
import com.example.maks2.weathertocoordinats.ui.BaseActivity;
import com.example.maks2.weathertocoordinats.ui.dialogs.MaterialDialogBuilder;
import com.example.maks2.weathertocoordinats.view_interfaces.iFavoritesActivityView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesActivity extends BaseActivity implements iFavoritesActivityView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.favorites_RV)
    RecyclerView favorites_RV;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectPresenter
    FavoritesActivityPresenter presenter;
    FavoritesAdapter favoritesAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Location> locations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            presenter.getWeatherCeveralCities(Location.listToString(presenter.getLocations()));
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        locations = presenter.getLocations();
        if (locations.size() != 0)
            presenter.getWeatherCeveralCities(Location.listToString(presenter.getLocations()));
        else
            MaterialDialogBuilder.createOneButton(this, R.string.oups, R.string.you_have_no_locations, this::finish);
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
                MaterialDialogBuilder.create(this, R.string.are_you_sure, R.string.clear_favorite_locations, () -> {
                    presenter.removeAllLocations();
                    finish();
                }, () -> {
                });
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @ProvidePresenter
    FavoritesActivityPresenter providePresenter() {
        return new FavoritesActivityPresenter(this);
    }

}
