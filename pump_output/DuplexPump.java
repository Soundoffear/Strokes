package com.oilfieldapps.allspark.strokescalculator.pump_output;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oilfieldapps.allspark.strokescalculator.R;

import java.text.DecimalFormat;

/**
 * Created by Allspark on 04/07/2017.
 */

public class DuplexPump extends Fragment {

    EditText input_liner_dia;
    EditText input_stroke_length;
    EditText input_rod_dia;
    EditText input_efficiency;

    TextView unit_liner_dia;
    TextView unit_stroke_length;
    TextView unit_rod_dia;
    TextView unit_pump_output;

    TextView output_pump_output;

    Button clear_btn;
    Button calculate_btn;

    final double DUPLEX_CONSTANT_FIRST = 0.000324;
    final double DUPLEX_CONSTANT_SECOND = 0.000162;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View duplexView = inflater.inflate(R.layout.pump_output_duplex_layout, container, false);

        input_liner_dia = duplexView.findViewById(R.id.duplex_liner_diameter_input);
        input_stroke_length = duplexView.findViewById(R.id.duplex_stroke_length_input);
        input_rod_dia = duplexView.findViewById(R.id.duplex_rod_diameter_input);
        input_efficiency = duplexView.findViewById(R.id.duplex_efficiency_input);

        unit_liner_dia = duplexView.findViewById(R.id.duplex_liner_diameter_unit);
        unit_stroke_length = duplexView.findViewById(R.id.duplex_stroke_length_unit);
        unit_rod_dia = duplexView.findViewById(R.id.duplex_rod_diameter_unit);
        unit_pump_output = duplexView.findViewById(R.id.duplex_pump_output_unit);

        output_pump_output = duplexView.findViewById(R.id.duplex_pump_output_value);

        clear_btn = duplexView.findViewById(R.id.duplex_clear_data);
        calculate_btn = duplexView.findViewById(R.id.duplex_calc_data);

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double liner_diameter = Double.parseDouble(input_liner_dia.getText().toString());
                    double stroke_length = Double.parseDouble(input_stroke_length.getText().toString());
                    double rod_diameter = Double.parseDouble(input_rod_dia.getText().toString());
                    double efficiency = Double.parseDouble(input_efficiency.getText().toString());

                    double firstPart = DUPLEX_CONSTANT_FIRST * Math.pow(liner_diameter, 2) * stroke_length;
                    double secondPart = DUPLEX_CONSTANT_SECOND * Math.pow(rod_diameter, 2) * stroke_length;

                    double efficiencyAt100 = firstPart - secondPart;
                    double realEfficiency = efficiencyAt100 * (efficiency / 100);

                    output_pump_output.setText(new DecimalFormat("0.000000").format(realEfficiency));

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

        return duplexView;
    }
}
