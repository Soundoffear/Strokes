package com.oilfieldapps.allspark.strokescalculator.data_in;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.custom_adapters.Hole_data_adapter;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleData;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleData_DataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 31/08/2017.
 */

public class HoleDisplayData extends Fragment {

    public HoleDisplayData() {

    }

    public List<HoleData> itemsData;
    public Hole_data_adapter listAdapter;
    public ListView dataListView;
    public FloatingActionButton fabButton;
    public ConstraintLayout constLayout;
    private HoleData holeData;
    private HoleData_DataBase holeData_dataBase;
    private Context tContext;
    private PopupWindow tPopUpWindow;

    private String diameter_chosen_units;
    private String length_chosen_units;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hole_data, container, false);

        dataListView = view.findViewById(R.id.listView_holeData);
        fabButton = view.findViewById(R.id.floatingActionButton_hole_data);
        constLayout = view.findViewById(R.id.hole_layout);
        holeData_dataBase = new HoleData_DataBase(getContext());
        tContext = getContext();
        itemsData = new ArrayList<>();
        itemsData = holeData_dataBase.getAllItem();
        listAdapter = new Hole_data_adapter(getActivity(), itemsData);

        dataListView.setAdapter(listAdapter);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreatePopUp();

            }
        });

        dataListView.setFocusable(true);

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                TextView nameTV = view.findViewById(R.id.itemNameLV);
                TextView idTV = view.findViewById(R.id.input_ID_LV_ET);
                TextView odTV = view.findViewById(R.id.input_OD_LV_ET);
                TextView topMD = view.findViewById(R.id.input_top_md_TV);
                TextView endMD = view.findViewById(R.id.input_end_md_TV);
                final String name = nameTV.getText().toString();
                String idString = idTV.getText().toString();
                String odString = odTV.getText().toString();
                String topMDString = topMD.getText().toString();
                String endMDString = endMD.getText().toString();
                final String[] oldDataStrings = {idString, odString, topMDString, endMDString};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("What would you like to do?");
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateData(name, position, oldDataStrings);
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listAdapter.remove(itemsData.get(position));
                        holeData_dataBase.removeFromDatabase(name);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private void CreatePopUp() {
        LayoutInflater inflater = (LayoutInflater) tContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.popup_window_layout_hole, null);

        tPopUpWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(Build.VERSION.SDK_INT >= 21) {
            tPopUpWindow.setElevation(10);
        }

        Button addButton = popUpView.findViewById(R.id.add_data);
        Button dismissButton = popUpView.findViewById(R.id.dismiss);

        final EditText inputName = popUpView.findViewById(R.id.input_name);
        final EditText inputID = popUpView.findViewById(R.id.input_ID);
        final EditText inputOD = popUpView.findViewById(R.id.input_OD);
        final EditText input_EndMD = popUpView.findViewById(R.id.input_end_md);
        final EditText input_TopMD = popUpView.findViewById(R.id.input_top_md);

        TextView idUnits = popUpView.findViewById(R.id.input_ID_units);
        TextView odUnits = popUpView.findViewById(R.id.input_OD_units);
        TextView end_MD_units = popUpView.findViewById(R.id.input_end_md_units);
        TextView top_MD_units = popUpView.findViewById(R.id.input_top_md_units);

        SharedPreferences unitsPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        diameter_chosen_units = unitsPreferences.getString("SNV_DIAMETER_UNITS", getResources().getString(R.string.in));
        length_chosen_units = unitsPreferences.getString("SNV_LENGTH_UNITS", getResources().getString(R.string.feet));
        idUnits.setText(diameter_chosen_units);
        odUnits.setText(diameter_chosen_units);
        end_MD_units.setText(length_chosen_units);
        top_MD_units.setText(length_chosen_units);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tPopUpWindow.dismiss();
            }
        });

        tPopUpWindow.setFocusable(true);
        tPopUpWindow.update();

        tPopUpWindow.showAtLocation(constLayout, Gravity.CENTER, 0, 0);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String string_inputName = inputName.getText().toString();
                    String string_inputID = inputID.getText().toString();
                    String string_inputOD = inputOD.getText().toString();
                    String string_inputEndMD = input_EndMD.getText().toString();
                    String string_inputTopMD = input_TopMD.getText().toString();

                    holeData = new HoleData(string_inputName, string_inputID, string_inputOD, string_inputEndMD, string_inputTopMD, diameter_chosen_units, length_chosen_units);
                    holeData_dataBase.addItemToHoleDesign(holeData);
                    itemsData.add(holeData);
                    dataListView.setAdapter(listAdapter);
                    tPopUpWindow.dismiss();
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
    }
    private void UpdateData(final String name, final int i, String[] oldData) {
        LayoutInflater inflater = (LayoutInflater) tContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.popup_window_layout_hole_update, null);

        tPopUpWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if(Build.VERSION.SDK_INT >= 21) {
            tPopUpWindow.setElevation(10);
        }

        Button addButton = popUpView.findViewById(R.id.add_data);
        Button dismissButton = popUpView.findViewById(R.id.dismiss);

        final EditText inputName = popUpView.findViewById(R.id.input_name);
        final EditText inputID = popUpView.findViewById(R.id.input_ID);
        final EditText inputOD = popUpView.findViewById(R.id.input_OD);
        final EditText input_EndMD = popUpView.findViewById(R.id.input_end_md);
        final EditText input_TopMD = popUpView.findViewById(R.id.input_top_md);
        inputName.setText(name);
        inputID.setText(oldData[0]);
        inputOD.setText(oldData[1]);
        input_TopMD.setText(oldData[2]);
        input_EndMD.setText(oldData[3]);

        TextView idUnits = popUpView.findViewById(R.id.input_ID_units);
        TextView odUnits = popUpView.findViewById(R.id.input_OD_units);
        TextView end_MD_units = popUpView.findViewById(R.id.input_end_md_units);
        TextView top_MD_units = popUpView.findViewById(R.id.input_top_md_units);

        SharedPreferences unitsPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        diameter_chosen_units = unitsPreferences.getString("SNV_DIAMETER_UNITS", getResources().getString(R.string.in));
        length_chosen_units = unitsPreferences.getString("SNV_LENGTH_UNITS", getResources().getString(R.string.feet));
        idUnits.setText(diameter_chosen_units);
        odUnits.setText(diameter_chosen_units);
        end_MD_units.setText(length_chosen_units);
        top_MD_units.setText(length_chosen_units);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tPopUpWindow.dismiss();
            }
        });

        tPopUpWindow.setFocusable(true);
        tPopUpWindow.update();

        tPopUpWindow.showAtLocation(constLayout, Gravity.CENTER, 0, 0);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String string_inputName = inputName.getText().toString();
                    String string_inputID = inputID.getText().toString();
                    String string_inputOD = inputOD.getText().toString();
                    String string_inputEndMD = input_EndMD.getText().toString();
                    String string_inputTopMD = input_TopMD.getText().toString();

                    holeData = new HoleData(string_inputName, string_inputID, string_inputOD, string_inputEndMD, string_inputTopMD, diameter_chosen_units, length_chosen_units);
                    holeData_dataBase.updateDatabase(holeData, name);
                    itemsData.get(i).setName(string_inputName);
                    itemsData.get(i).setInput_id(string_inputID);
                    itemsData.get(i).setInput_od(string_inputOD);
                    itemsData.get(i).setInput_end_md(string_inputEndMD);
                    itemsData.get(i).setInput_top_md(string_inputTopMD);
                    dataListView.setAdapter(listAdapter);
                    tPopUpWindow.dismiss();
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
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences unitsPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        diameter_chosen_units = unitsPreferences.getString("SNV_DIAMETER_UNITS", getResources().getString(R.string.in));
        length_chosen_units = unitsPreferences.getString("SNV_LENGTH_UNITS", getResources().getString(R.string.feet));

        holeData_dataBase.updateUnits(diameter_chosen_units, length_chosen_units);
        for(int i =0 ; i < itemsData.size(); i++) {
            itemsData.get(i).setInput_diameter_unit(diameter_chosen_units);
            itemsData.get(i).setInput_length_unit(length_chosen_units);
        }
        dataListView.setAdapter(listAdapter);
    }
}
