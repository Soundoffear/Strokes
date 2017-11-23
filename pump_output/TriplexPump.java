package com.oilfieldapps.allspark.strokescalculator.pump_output;

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

import com.oilfieldapps.allspark.strokescalculator.converters.Converter;
import com.oilfieldapps.allspark.strokescalculator.R;

import java.text.DecimalFormat;

/**
 * Created by Allspark on 04/07/2017.
 */

public class TriplexPump extends Fragment {

    EditText liner_input;
    EditText stroke_input;
    EditText efficiency_input;

    private final double TRIPLEX_CONSTANT = 0.000243;

    Button clear_data;
    Button calculate_data;

    TextView pump_output_result;

    TextView liner_unit;
    TextView stroke_unit;
    TextView output_unit;

    Converter converter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View triplexView = inflater.inflate(R.layout.pump_output_triplex_layout, container, false);

        converter = new Converter();

        liner_input = triplexView.findViewById(R.id.triplex_liner_diameter_input);
        stroke_input = triplexView.findViewById(R.id.triplex_stroke_length_input);
        efficiency_input = triplexView.findViewById(R.id.triplex_efficiency_input);

        pump_output_result = triplexView.findViewById(R.id.triplex_pump_output_result);

        liner_unit = triplexView.findViewById(R.id.triplex_liner_diameter_unit);
        stroke_unit = triplexView.findViewById(R.id.triplex_stroke_length_unit);
        output_unit = triplexView.findViewById(R.id.triplex_pump_output_unit);

        SetUnit();

        clear_data = triplexView.findViewById(R.id.triplex_clear_data);
        calculate_data = triplexView.findViewById(R.id.triplex_calculate_data);

        calculate_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String diameterUnit = liner_unit.getText().toString();
                    String lengthUnit = stroke_unit.getText().toString();
                    String outputUnit = output_unit.getText().toString();

                    double liner_diameter = Double.parseDouble(liner_input.getText().toString());
                    liner_diameter = converter.diameterConverter(diameterUnit, "in", liner_diameter);
                    double stroke_length = Double.parseDouble(stroke_input.getText().toString());
                    stroke_length = converter.lengthConverter(lengthUnit, "in", stroke_length);
                    double efficiency = Double.parseDouble(efficiency_input.getText().toString()) / 100;
                    Log.d("Values calculated: ", String.valueOf(liner_diameter) + " _ " + String.valueOf(stroke_length));
                    double pump_output_calculated = TRIPLEX_CONSTANT * Math.pow(liner_diameter, 2) * stroke_length * efficiency;
                    pump_output_calculated = converter.outputConverter("bbl/stk", outputUnit, pump_output_calculated);

                    pump_output_result.setText(new DecimalFormat("0.00000").format(RoundToFiveDecimals(pump_output_calculated)));

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

        return triplexView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SetUnit();
    }

    double RoundToFiveDecimals(double value) {
        double tempValue = value * 1000000;
        tempValue = Math.round(tempValue);
        double finalRounded = tempValue / 1000000;
        return finalRounded;
    }

    void SetUnit() {
        SharedPreferences triplexUnit = PreferenceManager.getDefaultSharedPreferences(getContext());
        String diameterUnit = triplexUnit.getString("TRIPLEX_DIAMETER_UNIT", getString(R.string.in));
        String lengthUnit = triplexUnit.getString("TRIPLEX_LENGTH_UNIT", getString(R.string.in));
        String outputUnit = triplexUnit.getString("TRIPLEX_OUTPUT_UNIT", getString(R.string.bbl_stk));

        liner_unit.setText(diameterUnit);
        stroke_unit.setText(lengthUnit);
        output_unit.setText(outputUnit);
    }
}
