package com.oilfieldapps.allspark.strokescalculator.menus;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.oilfieldapps.allspark.strokescalculator.R;

/**
 * Created by Allspark on 19/09/2017.
 */

public class TankVol_Menu extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.tank_vol_options);
    }
}
