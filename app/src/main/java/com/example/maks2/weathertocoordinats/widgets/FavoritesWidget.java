package com.example.maks2.weathertocoordinats.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

import java.util.Arrays;

/**
 * Sorry for this code from Railian Maksym (08.12.2017).
 */

public class FavoritesWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("WIDGET", "onUpdate " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d("WIDGET", "onDeleted " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("WIDGET", "Enabled ");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d("WIDGET", "onDisabled");
    }
}
