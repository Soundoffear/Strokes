package com.oilfieldapps.allspark.strokescalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_DataBase;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleData_DataBase;
import com.oilfieldapps.allspark.strokescalculator.data_in.DSDataDisplay;
import com.oilfieldapps.allspark.strokescalculator.data_in.HoleDisplayData;
import com.oilfieldapps.allspark.strokescalculator.menus.SNV_HowTo;
import com.oilfieldapps.allspark.strokescalculator.menus.SNV_Menu;

/**
 * Created by Allspark on 16/07/2017.
 */

public class SandV_Free extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HoleData_DataBase holeData_dataBase;
    private EditText pump_output_input;
    private TextView pump_output_units;


    DSDataDisplay dsDataDisplay;

    private Annulus_DataBase annulus_dataBase;

    private Button calculateBTN;
    private Button deleteBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_and_v_main_layout);

        holeData_dataBase = new HoleData_DataBase(getBaseContext());
        annulus_dataBase = new Annulus_DataBase(getBaseContext());
        pump_output_input = findViewById(R.id.snv_input_pump_output);
        pump_output_units = findViewById(R.id.snv_input_pump_output_units);
        dsDataDisplay = new DSDataDisplay();

        SharedPreferences preferences = getSharedPreferences("PUMP_OUTPUT_SAVE", MODE_PRIVATE);
        String pumpOutput;
        if (preferences != null) {
            pumpOutput = preferences.getString("PUMP_STRING", "0.00");
            pump_output_input.setText(pumpOutput);
        }

        toolbar = findViewById(R.id.snv_toolbar);
        tabLayout = findViewById(R.id.tabLayout_dataInput);
        viewPager = findViewById(R.id.viewPager_dataInput);

        calculateBTN = findViewById(R.id.snv_calculate);
        deleteBTN = findViewById(R.id.del_all);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Strokes and Volume");

        calculateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("PUMP_OUTPUT_SAVE", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String pump_input_string = pump_output_input.getText().toString();
                editor.putString("PUMP_STRING", pump_input_string);
                editor.commit();

                Intent data_to_results = new Intent(SandV_Free.this, SandV_Free_results.class);
                data_to_results.putExtra("STRING_PUMP_OUTPUT", pump_input_string);

                startActivity(data_to_results);

            }
        });

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() == 0) {
                    Fragment currentFragment = getFragmentAtPos(viewPager, viewPager.getCurrentItem());
                    HoleDisplayData holeDisplayData = (HoleDisplayData) currentFragment;
                    for(int i = 0; i < holeDisplayData.itemsData.size(); i++) {
                        holeDisplayData.listAdapter.remove(holeDisplayData.itemsData.get(i));
                        holeDisplayData.dataListView.setAdapter(holeDisplayData.listAdapter);
                    }
                    holeData_dataBase.deleteDB();
                    Toast.makeText(getBaseContext(), "Hole Data Cleared", Toast.LENGTH_SHORT).show();
                } else if (viewPager.getCurrentItem() == 1) {
                    Fragment currentFragment = getFragmentAtPos(viewPager, viewPager.getCurrentItem());
                    DSDataDisplay dsDataDisplay = (DSDataDisplay) currentFragment;
                    for(int i = 0; i < dsDataDisplay.annulusDataList.size();i++) {
                        dsDataDisplay.annulus_data_adapter.remove(dsDataDisplay.annulusDataList.get(i));
                        dsDataDisplay.ds_listView.setAdapter(dsDataDisplay.annulus_data_adapter);
                    }
                    annulus_dataBase.deleteDB();
                    Toast.makeText(getBaseContext(), "Drill String Data Cleared", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Fragment getFragmentAtPos(ViewPager container, int position) {
        String tag_name = makeFragmentTagName(container.getId(), position);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag_name);
        return fragment;
    }

    private String makeFragmentTagName (int viewId, int index) {
        return "android:switcher:" + viewId+":"+index;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.snv_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.snv_units) {
            Intent intent = new Intent(getApplicationContext(), SNV_Menu.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.snv_howTo) {
            Intent intent = new Intent(getApplicationContext(), SNV_HowTo.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HoleDisplayData();
                case 1:
                    return new DSDataDisplay();
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
                    return "Hole Data";
                case 1:
                    return "Drill String Data";
            }
            return null;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String pumpOutputUnits = sharedPreferences.getString("SNV_PUMP_OUTPUT", getResources().getString(R.string.bbl_stk));

        pump_output_units.setText(pumpOutputUnits);
    }

}
