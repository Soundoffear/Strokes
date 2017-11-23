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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.oilfieldapps.allspark.strokescalculator.menus.PumpOutputMenu;
import com.oilfieldapps.allspark.strokescalculator.pump_output.DuplexPump;
import com.oilfieldapps.allspark.strokescalculator.pump_output.TriplexPump;

/**
 * Created by Allspark on 04/07/2017.
 */

public class PumpOutput extends AppCompatActivity {

    Toolbar pump_output_toolbar;
    TabLayout pump_output_tabLayout;
    ViewPager pump_output_viewPager;
    OutputPageAdapter outputPageAdapter;

    private AdView adView;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pump_output_main);

        adView = findViewById(R.id.po_adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        pump_output_toolbar = findViewById(R.id.pump_output_toolbar);
        pump_output_tabLayout = findViewById(R.id.pump_output_tabLayout);
        pump_output_viewPager = findViewById(R.id.pump_output_viewPager);
        outputPageAdapter = new OutputPageAdapter(getSupportFragmentManager());

        setSupportActionBar(pump_output_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Pump Output");
        pump_output_toolbar.setTitleTextColor(getResources().getColor(R.color.textColor));

        pump_output_viewPager.setAdapter(outputPageAdapter);
        pump_output_tabLayout.setupWithViewPager(pump_output_viewPager);
    }

    private class OutputPageAdapter extends FragmentPagerAdapter {

        public OutputPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TriplexPump();
                case 1:
                    return new DuplexPump();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Triplex Pump";
                case 1:
                    return "Duplex Pump";
            }

            return super.getPageTitle(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pump_output_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.pump_output_menu) {
            Intent intent = new Intent(getApplicationContext(), PumpOutputMenu.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
