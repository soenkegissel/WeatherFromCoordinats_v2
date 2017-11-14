package com.example.maks2.weathertocoordinats.managers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;

import com.example.maks2.weathertocoordinats.ui.BaseActivity;

/**
 * Sorry for this code from Railian Maksym (14.11.2017).
 */

public class FragmentsManager {
    private FragmentManager mFragmentManager;
    private int mViewContainer;

    public FragmentsManager(Context context, int viewContainer) {
        mViewContainer = viewContainer;
        mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(mViewContainer, fragment);
        fragmentTransaction.commit();
    }

}
