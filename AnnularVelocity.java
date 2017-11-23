package com.oilfieldapps.allspark.strokescalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.oilfieldapps.allspark.strokescalculator.converters.Converter;
import com.oilfieldapps.allspark.strokescalculator.menus.AV_Menu;

/**
 * Created by Allspark on 17/09/2017.
 */

public class AnnularVelocity extends AppCompatActivity {

    private Converter converter;

    private Toolbar av_toolbar;

    private EditText et_pumpOutput, et_spm, et_id, et_od;

    private TextView tv_av_result;

    private TextView units_pumpOutput, units_id, units_od, units_result;

    private String u_pumpOutput, u_dia, u_av;

    private Button calculate_BTN, clear_BTN;

    private AdView av_adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_main);

        av_adView = findViewById(R.id.av_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        av_adView.loadAd(adRequest);

        converter = new Converter();

        av_toolbar = findViewById(R.id.av_toolbar);

        setSupportActionBar(av_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.annular_velocity));

        et_pumpOutput = findViewById(R.id.av_et_pump_output_input);
        et_spm = findViewById(R.id.av_et_spm_input);
        et_id = findViewById(R.id.av_et_id_input);
        et_od = findViewById(R.id.av_et_od_input);

        units_pumpOutput = findViewById(R.id.av_et_pump_output_unit);
        units_id = findViewById(R.id.av_et_id_unit);
        units_od = findViewById(R.id.av_et_od_unit);
        units_result = findViewById(R.id.av_ann_vel_results_units);

        SetAllUnits();

        tv_av_result = findViewById(R.id.av_ann_vel_results);

        calculate_BTN = findViewById(R.id.av_calculate);
        clear_BTN = findViewById(R.id.av_calculate);

        clear_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zero = getResources().getString(R.string.zero);
                et_pumpOutput.setText(zero);
                et_spm.setText(zero);
                et_id.setText(zero);
                et_od.setText(zero);
            }
        });

        calculate_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double pumpOutput = Double.parseDouble(et_pumpOutput.getText().toString());
                    double spm = Double.parseDouble(et_spm.getText().toString());
                    double id = Double.parseDouble(et_id.getText().toString());
                    double od = Double.parseDouble(et_od.getText().toString());

                    pumpOutput = converter.outputConverter(u_pumpOutput, getResources().getString(R.string.bbl_stk), pumpOutput);
                    id = converter.diameterConverter(u_dia, getResources().getString(R.string.in), id);
                    od = converter.diameterConverter(u_dia, getResources().getString(R.string.in), od);

                    double pumpOutput_in_volPerTime = pumpOutput * spm;
                    double annular_vol_per_length = 1029.4 / (Math.pow(id, 2) - Math.pow(od, 2));

                    double annular_velocity = pumpOutput_in_volPerTime * annular_vol_per_length;

                    annular_velocity = converter.annularVelocityConverter(getResources().getString(R.string.ft_min), u_av, annular_velocity);

                    annular_velocity = RoundUpToTwoDec(annular_velocity);

                    String av_string = String.valueOf(annular_velocity);

                    tv_av_result.setText(av_string);
                } catch (NumberFormatException nfe) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                    builder.setTitle("Warning");
                    builder.setMessage("Please check if all the field contains numbers");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

    }

    private void SetAllUnits() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        u_pumpOutput = sharedPreferences.getString(getResources().getString(R.string.PREFS_AV_PO_UNIT), getResources().getString(R.string.bbl_stk));
        u_dia = sharedPreferences.getString(getResources().getString(R.string.PREFS_AV_DIA_UNIT), getResources().getString(R.string.in));
        u_av = sharedPreferences.getString(getResources().getString(R.string.PREFS_AV_SPEED_UNITS), getResources().getString(R.string.ft_min));

        units_pumpOutput.setText(u_pumpOutput);
        units_id.setText(u_dia);
        units_od.setText(u_dia);
        units_result.setText(u_av);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.av_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.av_menu) {
            Intent prefsIntent = new Intent(AnnularVelocity.this, AV_Menu.class);
            startActivity(prefsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public double RoundUpToTwoDec(double ann_vel) {
        ann_vel = ann_vel * 100;
        ann_vel = Math.round(ann_vel);
        ann_vel = ann_vel / 100;
        return ann_vel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetAllUnits();
    }
}
