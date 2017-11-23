package com.oilfieldapps.allspark.strokescalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.oilfieldapps.allspark.strokescalculator.data_out.SNV_Results_DrillString;
import com.oilfieldapps.allspark.strokescalculator.data_out.SNV_Results_EmptyHole;
import com.oilfieldapps.allspark.strokescalculator.data_out.SNV_Results_Annulus;

/**
 * Created by Allspark on 16/07/2017.
 */

public class SandV_Free_results extends AppCompatActivity {

    Toolbar snv_results_toolbar;
    ViewPager snv_results_viewPager;
    TabLayout snv_results_tabLayout;
    private AdView adView;

    public static String pump_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.s_and_v_results_main_layout);

        adView = findViewById(R.id.testAdd);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Intent intent = getIntent();
        pump_output = intent.getStringExtra("STRING_PUMP_OUTPUT");

        snv_results_viewPager = findViewById(R.id.viewPager_resultScreen);
        snv_results_tabLayout = findViewById(R.id.tabLayout_resultScreen);

        snv_results_viewPager.setAdapter(new ResultsPageViewerAdapter(getSupportFragmentManager()));
        snv_results_tabLayout.setupWithViewPager(snv_results_viewPager);

        snv_results_toolbar = findViewById(R.id.snv_results_toolbar);
        setSupportActionBar(snv_results_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Results");

    }

    private class ResultsPageViewerAdapter extends FragmentPagerAdapter {

        public ResultsPageViewerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new SNV_Results_EmptyHole();
                case 1: return new SNV_Results_DrillString();
                case 2: return new SNV_Results_Annulus();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Empty Hole";
                case 1: return "Drill String";
                case 2: return "Annular";
            }
            return null;
        }
    }

}
