package com.example.maks2.weathertocoordinats.managers;

import android.content.Context;

import com.example.maks2.weathertocoordinats.R;

/**
 * Sorry for this code from Railian Maksym (03.12.2017).
 */

public class HttpErrorViewManager {
    private static final String http400 = String.valueOf(400);
    private static final String http404 = String.valueOf(404);
    private static final String http500 = String.valueOf(500);
    private static final String noInternet = "Unable to resolve host";

    public static String convertToText(Context context,String e) {
        String output;
        if (e.contains(http400)) output = context.getString(R.string.check_info);
        else if (e.contains(http404)) output = context.getString(R.string.incorrect_location);
        else if (e.contains(http500)) output = context.getString(R.string.server_not_respond);
        else if (e.contains(noInternet)) output = context.getString(R.string.no_internet);
        else output = e;
        return output;
    }
}
