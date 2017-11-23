package com.oilfieldapps.allspark.strokescalculator.data_out;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.SandV_Free;
import com.oilfieldapps.allspark.strokescalculator.SandV_Free_results;
import com.oilfieldapps.allspark.strokescalculator.converters.Converter;
import com.oilfieldapps.allspark.strokescalculator.custom_adapters.DrillString_results_adapter;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_Data;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_DataBase;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.DrillString_Results;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.DrillString_Results_DataBase;

import java.util.List;

/**
 * Created by Allspark on 10/09/2017.
 */

public class SNV_Results_DrillString extends Fragment {

    //data in
    ListView drill_string_results_list_view;
    Annulus_DataBase annulus_dataBase;
    List<Annulus_Data> annulus_dataList;
    double pump_output;

    //data out
    DrillString_Results drillString_results;
    DrillString_Results_DataBase drillString_results_dataBase;
    DrillString_results_adapter drillString_results_adapter;
    List<DrillString_Results> drillStringResultsList;

    Converter converter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.s_and_v_data_out_drill_string, null);

        converter = new Converter();

        drill_string_results_list_view = view.findViewById(R.id.list_view_drill_string_results);
        annulus_dataBase = new Annulus_DataBase(getContext());
        annulus_dataList = annulus_dataBase.getAllItems();

        drillString_results_dataBase = new DrillString_Results_DataBase(getContext());
        drillString_results_dataBase.deleteDB();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String volumeUnits = sharedPreferences.getString("SNV_VOLUME_UNITS", getResources().getString(R.string.bbl));
        String pumpOutputUnits = sharedPreferences.getString("SNV_PUMP_OUTPUT", getResources().getString(R.string.bbl_stk));

        pump_output = Double.parseDouble(SandV_Free_results.pump_output);
        double totalVolume = 0;
        double totalStrokes = 0;
        //Calculate
        for(int i = 0; i < annulus_dataList.size(); i++) {
            String name = annulus_dataList.get(i).getString_name();
            String diameter_units = annulus_dataList.get(i).getDiameter_units();
            String length_units = annulus_dataList.get(i).getLength_units();
            double id = Double.parseDouble(annulus_dataList.get(i).getString_id());
            id = converter.diameterConverter(diameter_units, getResources().getString(R.string.in), id);
            double length = Double.parseDouble(annulus_dataList.get(i).getString_length());
            length = converter.lengthConverter(length_units, getResources().getString(R.string.feet), length);

            double volume = Math.pow(id, 2) / 1029.4 * length;
            double volumeConverted = converter.VolumeConverter(getResources().getString(R.string.bbl), volumeUnits, volume);
            pump_output = converter.outputConverter(pumpOutputUnits, getResources().getString(R.string.bbl_stk), pump_output);
            double strokes = volume / pump_output;
            strokes = Math.round(strokes);
            volumeConverted = roundUpToTwoDec(volumeConverted);
            String volumeString = String.valueOf(volumeConverted);
            drillString_results = new DrillString_Results(name, volumeString, String.valueOf(strokes), volumeUnits);
            drillString_results_dataBase.inputItem(drillString_results);
            totalVolume = totalVolume + volumeConverted;
            totalVolume = roundUpToTwoDec(totalVolume);
            totalStrokes = totalStrokes + strokes;

        }
        String totalName = "Total Drill String Values";
        drillString_results = new DrillString_Results(totalName, String.valueOf(totalVolume), String.valueOf(totalStrokes), volumeUnits);
        drillString_results_dataBase.inputItem(drillString_results);

        drillStringResultsList = drillString_results_dataBase.getAllItems();
        drillString_results_adapter = new DrillString_results_adapter(getContext(), drillStringResultsList);
        drill_string_results_list_view.setAdapter(drillString_results_adapter);

        return view;
    }

    public double roundUpToTwoDec(double dataIn) {
        double dataOut = dataIn * 100;
        dataOut = Math.round(dataOut);
        dataOut = dataOut / 100;
        return dataOut;
    }
}
