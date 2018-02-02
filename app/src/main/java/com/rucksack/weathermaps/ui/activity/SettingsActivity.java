package com.rucksack.weathermaps.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rucksack.weathermaps.MyApplication;
import com.rucksack.weathermaps.R;
import com.rucksack.weathermaps.managers.SharedPreferencesManager;
import com.rucksack.weathermaps.ui.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsActivity extends BaseActivity {
    @BindView(R.id.units_spinner)
    Spinner spinner;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        ((MyApplication)getApplication()).getAppComponent().inject(this);
        android.support.v7.widget.Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        String units = sharedPreferencesManager.getString("units");
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.unitslist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        for(int i=0;i<=getResources().getStringArray(R.array.unitslist).length-1;i++){
            if (getResources().getStringArray(R.array.unitslist)[i].equals(units)){
                spinner.setSelection(i);
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_apply:
                sharedPreferencesManager.putString("units",spinner.getSelectedItem().toString());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
