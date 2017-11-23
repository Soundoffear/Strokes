package com.oilfieldapps.allspark.strokescalculator.menus;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.oilfieldapps.allspark.strokescalculator.R;

/**
 * Created by Allspark on 05/07/2017.
 */

public class PumpOutputMenu extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pump_output_options);
    }
}
