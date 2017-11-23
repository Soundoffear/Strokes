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
import com.oilfieldapps.allspark.strokescalculator.custom_adapters.Annulus_results_adapter;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_Data;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_DataBase;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_Results;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_Results_DataBase;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleData;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleData_DataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 07/09/2017.
 */

public class SNV_Results_Annulus extends Fragment {

    // input data
    private Annulus_DataBase annulus_dataBase;
    private HoleData_DataBase holeData_dataBase;
    private List<Annulus_Data> annulusDataList;
    private List<HoleData> holeDataList;
    private double pumpOutput;

    // output data
    private ListView output_listView_drillString;
    private Annulus_Results annulusResults;
    private Annulus_Results_DataBase annulusResults_dataBase;
    private Annulus_results_adapter annulus_results_adapter;
    private List<Annulus_Results> annulusResultsList;

    private Converter converter;

    private String volumeUnits;
    private String pumpOutputUnits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_and_v_data_out_annular, null);

        converter = new Converter();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        volumeUnits = sharedPreferences.getString("SNV_VOLUME_UNITS", getResources().getString(R.string.bbl));
        pumpOutputUnits = sharedPreferences.getString("SNV_PUMP_OUTPUT", getResources().getString(R.string.bbl_stk));

        pumpOutput = Double.parseDouble(SandV_Free_results.pump_output);
        pumpOutput = converter.outputConverter(pumpOutputUnits, getResources().getString(R.string.bbl_stk), pumpOutput);
        annulus_dataBase = new Annulus_DataBase(getContext());
        holeData_dataBase = new HoleData_DataBase(getContext());
        output_listView_drillString = view.findViewById(R.id.list_view_annulus_results);
        annulusResults_dataBase = new Annulus_Results_DataBase(getContext());
        annulusResults_dataBase.delateDatabase();
        CalculateAllData();
        annulusResultsList = annulusResults_dataBase.getAllItem();
        annulus_results_adapter = new Annulus_results_adapter(getContext(), annulusResultsList);
        output_listView_drillString.setAdapter(annulus_results_adapter);

        return view;
    }

    private double startingData = 0;
    private double closingData = 0;
    private double tempCalculatedLength = 0;
    private double volumePerStage = 0;
    private double strokesPerStage = 0;
    private double totalStrokes = 0;
    private double totalVolume = 0;
    private String DS_partName; // to be used later for displaying details

    public void CalculateAllData() {

        totalStrokes = 0;
        totalVolume = 0;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String volumeUnits = sharedPreferences.getString("SNV_VOLUME_UNITS", getResources().getString(R.string.bbl));

        annulusDataList = annulus_dataBase.getAllItems();

        List<Double> sumOfAllDSLengths = new ArrayList<>();
        double tempSum = 0;
        for (int ds = 0; ds < annulusDataList.size(); ds++) {
            tempSum = tempSum + Double.parseDouble(annulusDataList.get(ds).getString_length());
            sumOfAllDSLengths.add(tempSum);
        }

        holeDataList = holeData_dataBase.getAllItem();

        // Annular volume calculator

        for (int i = 0; i < holeDataList.size(); i++) {
            closingData = Double.parseDouble(holeDataList.get(i).getInput_end_md());
            String length_unit = holeDataList.get(i).getInput_length_unit();
            String diameter_unit = holeDataList.get(i).getInput_diameter_unit();
            Log.d("<Start, End>", "<" + startingData + "," + closingData + ">");
            for (int j = 0; j < sumOfAllDSLengths.size(); j++) {
                if (sumOfAllDSLengths.get(j) > startingData && sumOfAllDSLengths.get(j) <= closingData) {
                    if (i > 0) {
                        if (j > 0) {
                            if (sumOfAllDSLengths.get(j) > startingData && sumOfAllDSLengths.get(j - 1) < startingData) {
                                tempCalculatedLength = sumOfAllDSLengths.get(j) - startingData;
                                DS_partName = annulusDataList.get(j).getString_name();
                                double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                                double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                                double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                                double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                                double calculatedStrokes = tempVolForStrokes / pumpOutput;
                                volumePerStage = volumePerStage + calculatedVolume;
                                strokesPerStage = strokesPerStage + calculatedStrokes;
                                Log.d("1.01 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)));
                            } else {
                                if (j < sumOfAllDSLengths.size() - 1) {
                                    tempCalculatedLength = sumOfAllDSLengths.get(j) - sumOfAllDSLengths.get(j - 1);
                                    DS_partName = annulusDataList.get(j).getString_name();
                                    double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                                    double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                                    double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                                    double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                                    double calculatedStrokes = tempVolForStrokes / pumpOutput;
                                    volumePerStage = volumePerStage + calculatedVolume;
                                    strokesPerStage = strokesPerStage + calculatedStrokes;
                                    Log.d("N.N1 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)));
                                }
                            }
                        } else {
                            tempCalculatedLength = sumOfAllDSLengths.get(j) - startingData;
                            DS_partName = annulusDataList.get(j).getString_name();
                            double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                            double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                            double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                            double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                            double calculatedStrokes = tempVolForStrokes / pumpOutput;
                            volumePerStage = volumePerStage + calculatedVolume;
                            strokesPerStage = strokesPerStage + calculatedStrokes;
                            Log.d("1.02 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)));
                        }
                    } else if (i == 0) {
                        if (j == i) {
                            tempCalculatedLength = sumOfAllDSLengths.get(j) - startingData;
                            DS_partName = annulusDataList.get(j).getString_name();
                            double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                            double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                            double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                            double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                            double calculatedStrokes = tempVolForStrokes / pumpOutput;
                            volumePerStage = volumePerStage + calculatedVolume;
                            strokesPerStage = strokesPerStage + calculatedStrokes;
                            Log.d("1.03 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)) + " " + String.valueOf(startingData));
                        } else if (j > i) {
                            if (j < (sumOfAllDSLengths.size() - 1)) {
                                tempCalculatedLength = sumOfAllDSLengths.get(j) - sumOfAllDSLengths.get(j - 1);
                                DS_partName = annulusDataList.get(j).getString_name();
                                double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                                double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                                double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                                double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                                double calculatedStrokes = tempVolForStrokes / pumpOutput;
                                volumePerStage = volumePerStage + calculatedVolume;
                                strokesPerStage = strokesPerStage + calculatedStrokes;
                                Log.d("N.N2 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)));
                            }
                        }
                    }
                    if (j - 1 >= 0) {
                        if (j < sumOfAllDSLengths.size() - 1) {
                            if (sumOfAllDSLengths.get(j + 1) > closingData) {
                                tempCalculatedLength = closingData - sumOfAllDSLengths.get(j);
                                DS_partName = annulusDataList.get(j).getString_name();
                                double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                                double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                                double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                                double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                                double calculatedStrokes = tempVolForStrokes / pumpOutput;
                                volumePerStage = volumePerStage + calculatedVolume;
                                strokesPerStage = strokesPerStage + calculatedStrokes;
                                Log.d("L.L1 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)));
                                break;
                            }
                        } else if (j == (sumOfAllDSLengths.size() - 1)) {
                            if (sumOfAllDSLengths.get(j) <= closingData) {
                                if (sumOfAllDSLengths.get(j - 1) < startingData) {
                                    break;
                                }
                                tempCalculatedLength = sumOfAllDSLengths.get(j) - sumOfAllDSLengths.get(j - 1);
                                DS_partName = annulusDataList.get(j).getString_name();
                                double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                                double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                                double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                                double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                                double calculatedStrokes = tempVolForStrokes / pumpOutput;
                                volumePerStage = volumePerStage + calculatedVolume;
                                strokesPerStage = strokesPerStage + calculatedStrokes;
                                Log.d("L.L2 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)));
                            }
                        }
                    }
                } else if (sumOfAllDSLengths.get(j) > startingData && sumOfAllDSLengths.get(j) > closingData) {
                    if (startingData == 0) {
                        if (j > 0) {
                            tempCalculatedLength = sumOfAllDSLengths.get(j) - (sumOfAllDSLengths.get(j) - closingData) - sumOfAllDSLengths.get(j - 1);
                        } else if (j == 0) {
                            tempCalculatedLength = sumOfAllDSLengths.get(j) - (sumOfAllDSLengths.get(j) - closingData);
                        }

                        DS_partName = annulusDataList.get(j).getString_name();
                        double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                        double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                        double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                        double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                        double calculatedStrokes = tempVolForStrokes / pumpOutput;
                        volumePerStage = volumePerStage + calculatedVolume;
                        strokesPerStage = strokesPerStage + calculatedStrokes;
                        Log.d("1.1 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)));
                        break;
                    } else {
                        if (j == 0) {
                            tempCalculatedLength = closingData - startingData;
                        } else {
                            tempCalculatedLength = closingData - sumOfAllDSLengths.get(j - 1);
                        }
                        DS_partName = annulusDataList.get(j).getString_name();
                        double od = Double.parseDouble(annulusDataList.get(j).getString_od());
                        double id = Double.parseDouble(holeDataList.get(i).getInput_id());
                        double calculatedVolume = CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit);
                        double tempVolForStrokes = converter.VolumeConverter(volumeUnits, getResources().getString(R.string.bbl), calculatedVolume);
                        double calculatedStrokes = tempVolForStrokes / pumpOutput;
                        volumePerStage = volumePerStage + calculatedVolume;
                        strokesPerStage = strokesPerStage + calculatedStrokes;
                        Log.d("N.N3 -->", String.valueOf(CalculateVolume(tempCalculatedLength, id, od, diameter_unit, length_unit)));
                        break;
                    }
                }
            }
            String results_string_ds = holeDataList.get(i).getName();
            volumePerStage = roundUpToTwoDec(volumePerStage);
            strokesPerStage = roundUpToTwoDec(strokesPerStage);
            annulusResults = new Annulus_Results(results_string_ds, String.valueOf(volumePerStage), String.valueOf(strokesPerStage), volumeUnits);
            annulusResults_dataBase.inputData(annulusResults);
            startingData = closingData;
            totalStrokes = totalStrokes + strokesPerStage;
            totalVolume = totalVolume + volumePerStage;
            volumePerStage = 0;
            strokesPerStage = 0;
        }
        String results_total_string = "Total Annular Values";
        totalVolume = roundUpToTwoDec(totalVolume);
        totalStrokes = roundUpToTwoDec(totalStrokes);
        annulusResults = new Annulus_Results(results_total_string, String.valueOf(totalVolume), String.valueOf(totalStrokes), volumeUnits);
        annulusResults_dataBase.inputData(annulusResults);
        startingData = 0;
    }

    public double CalculateVolume(double length, double id, double od, String diameterUnit, String lengthUnit) {
        id = converter.diameterConverter(diameterUnit, getResources().getString(R.string.in), id);
        od = converter.diameterConverter(diameterUnit, getResources().getString(R.string.in), od);
        length = converter.lengthConverter(lengthUnit, getResources().getString(R.string.feet), length);
        double volume = ((Math.pow(id, 2) - Math.pow(od, 2)) / 1029.4) * length;
        volume = converter.VolumeConverter("bbl", volumeUnits, volume);
        return volume;
    }

    public double roundUpToTwoDec(double dataIn) {
        double dataOut = dataIn * 100;
        dataOut = Math.round(dataOut);
        dataOut = dataOut / 100;
        return dataOut;
    }
}
