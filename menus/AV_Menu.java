package com.oilfieldapps.allspark.strokescalculator.menus;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.oilfieldapps.allspark.strokescalculator.R;

/**
 * Created by Allspark on 17/09/2017.
 */

public class AV_Menu extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.av_options);
    }
}
