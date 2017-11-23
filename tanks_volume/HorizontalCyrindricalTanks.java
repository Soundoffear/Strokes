package com.oilfieldapps.allspark.strokescalculator.tanks_volume;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.converters.Converter;

/**
 * Created by Allspark on 18/09/2017.
 */

public class HorizontalCyrindricalTanks extends Fragment {

    Converter converter;
    //input data
    private EditText diameter_et, length_et, fluid_level_et;

    private Button calculate_btn, clear_btn;

    //units
    private TextView diameter_units_tv, length_units_tv, fluid_level_units_tv;
    private TextView total_volume_units_tv, fluid_volume_units_tv;

    //output data
    private TextView total_vol_results_tv, fluid_vol_results_tv;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.tanks_volume_hor_cylindrical_tank, null);

        converter = new Converter();

        diameter_et = layoutView.findViewById(R.id.tank_vol_hc_diameter_input);
        length_et = layoutView.findViewById(R.id.tank_vol_hc_length_input);
        fluid_level_et = layoutView.findViewById(R.id.tank_vol_hc_fluid_level_input);

        total_vol_results_tv = layoutView.findViewById(R.id.tank_vol_hc_total_volume_result);
        fluid_vol_results_tv = layoutView.findViewById(R.id.tank_vol_hc_fluid_volume_result);

        calculate_btn = layoutView.findViewById(R.id.tank_volume_hc_calculate);
        clear_btn = layoutView.findViewById(R.id.tank_volume_hc_clear);

        diameter_units_tv = layoutView.findViewById(R.id.tank_vol_hc_diameter_units);
        length_units_tv = layoutView.findViewById(R.id.tank_vol_hc_length_units);
        fluid_level_units_tv = layoutView.findViewById(R.id.tank_vol_hc_fluid_level_units);
        total_volume_units_tv = layoutView.findViewById(R.id.tank_vol_hc_total_volume_result_units);
        fluid_volume_units_tv = layoutView.findViewById(R.id.tank_vol_hc_fluid_volume_result_units);

        SetAllUnits();

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double diameter = Double.parseDouble(diameter_et.getText().toString());
                    diameter = converter.diameterConverter(diameter_units_tv.getText().toString(), getResources().getString(R.string.in), diameter);
                    double length = Double.parseDouble(length_et.getText().toString());
                    length = converter.diameterConverter(length_units_tv.getText().toString(), getResources().getString(R.string.in), length);
                    double fluid_level = Double.parseDouble(fluid_level_et.getText().toString());
                    fluid_level = converter.diameterConverter(fluid_level_units_tv.getText().toString(), getResources().getString(R.string.in), fluid_level);

                    double total_tank_volume = Math.pow(diameter, 2) / 4 * Math.PI * length;
                    total_tank_volume = converter.VolumeConverter("in3", total_volume_units_tv.getText().toString(), total_tank_volume);

                    total_vol_results_tv.setText(String.valueOf(RoundToTwoDec(total_tank_volume)));

                    double radius = diameter / 2;
                    double arccos = (Math.acos((radius - fluid_level) / radius));

                    double fluidVolume = length * (Math.pow(radius, 2) * arccos - (radius - fluid_level) * Math.sqrt(2 * radius * fluid_level - Math.pow(fluid_level, 2)));
                    fluidVolume = converter.VolumeConverter("in3", fluid_volume_units_tv.getText().toString(), fluidVolume);
                    Log.d("FLUID Vol: ", String.valueOf(fluidVolume) + " " + radius);

                    fluid_vol_results_tv.setText(String.valueOf(RoundToTwoDec(fluidVolume)));
                } catch (NumberFormatException nfe) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zero = getResources().getString(R.string.zero);
                diameter_et.setText(zero);
                length_et.setText(zero);
                fluid_level_et.setText(zero);
                total_vol_results_tv.setText(zero);
                fluid_vol_results_tv.setText(zero);
            }
        });

        return layoutView;
    }

    private double RoundToTwoDec(double numberToRound) {
        double roundedNumber = numberToRound * 100;
        roundedNumber = Math.round(roundedNumber);
        roundedNumber = roundedNumber/100;
        return roundedNumber;
    }

    private void SetAllUnits() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String diameterUnits = sharedPreferences.getString("TANK_DIAMETER_UNITS", getResources().getString(R.string.in));
        String lengthUnits = sharedPreferences.getString("TANK_LENGTH_UNITS", getResources().getString(R.string.in));
        String volumeUnits = sharedPreferences.getString("TANK_VOLUME_UNITS", getResources().getString(R.string.bbl));

        diameter_units_tv.setText(diameterUnits);
        length_units_tv.setText(lengthUnits);
        fluid_level_units_tv.setText(lengthUnits);
        total_volume_units_tv.setText(volumeUnits);
        fluid_volume_units_tv.setText(volumeUnits);
    }

    @Override
    public void onResume() {
        super.onResume();
        SetAllUnits();
    }
}
