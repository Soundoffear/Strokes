package com.oilfieldapps.allspark.strokescalculator.data_out;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.converters.Converter;
import com.oilfieldapps.allspark.strokescalculator.custom_adapters.Hole_results_no_pipe_adapter;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleData;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleData_DataBase;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleResultsData;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleResults_DataBase;

import java.util.List;

/**
 * Created by Allspark on 07/09/2017.
 */

public class SNV_Results_EmptyHole extends Fragment {

    HoleData_DataBase holeData_dataBase;

    List<HoleData> holeDataList;
    List<HoleResultsData> holeResultsDatas;

    ListView holeDataResultsListView;

    HoleResultsData holeResultsData;
    HoleResults_DataBase holeResults_dataBase;
    Hole_results_no_pipe_adapter hole_results_no_pipe_adapter;

    Converter converter;
    String volumeUnits;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.s_and_v_data_out_empty_hole, null);
        holeData_dataBase = new HoleData_DataBase(getContext());
        holeDataResultsListView = view.findViewById(R.id.listView_results_hole);
        holeResults_dataBase = new HoleResults_DataBase(getContext());
        holeResults_dataBase.deleteDatabase();
        holeDataList = holeData_dataBase.getAllItem();

        converter = new Converter();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        volumeUnits = sharedPreferences.getString("SNV_VOLUME_UNITS", getResources().getString(R.string.bbl));

        for(int i =0; i< holeDataList.size();i++) {
            double resultVolume = CalculateVolume(holeDataList.get(i));
            resultVolume = RoundUpToTwoDec(resultVolume);
            String name = holeDataList.get(i).getName();
            holeResultsData = new HoleResultsData(name, String.valueOf(resultVolume), volumeUnits);
            holeResults_dataBase.insertData(holeResultsData);
        }

        holeResultsDatas = holeResults_dataBase.getAllResults();

        hole_results_no_pipe_adapter = new Hole_results_no_pipe_adapter(getContext(), holeResultsDatas);
        holeDataResultsListView.setAdapter(hole_results_no_pipe_adapter);

        return view;
    }

    public double CalculateVolume(HoleData data) {

        String length_units = data.getInput_length_unit();
        String diameter_units = data.getInput_diameter_unit();
        double endMD = Double.parseDouble(data.getInput_end_md());
        endMD = converter.lengthConverter(length_units, getResources().getString(R.string.feet), endMD);
        double topMD = Double.parseDouble(data.getInput_top_md());
        topMD = converter.lengthConverter(length_units, getResources().getString(R.string.feet), topMD);
        double id = Double.parseDouble(data.getInput_id());
        id = converter.diameterConverter(diameter_units, getResources().getString(R.string.in), id);

        double volume = Math.pow(id, 2) / 1029.4 * (endMD - topMD);

        volume = converter.VolumeConverter(getResources().getString(R.string.bbl), volumeUnits, volume);

        return volume;
    }

    public double RoundUpToTwoDec(double number) {
        double twoDecNumber = number * 100;
        twoDecNumber = Math.round(twoDecNumber);
        twoDecNumber = twoDecNumber / 100;
        return twoDecNumber;
    }
}
