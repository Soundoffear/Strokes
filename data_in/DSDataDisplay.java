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
import android.util.Log;
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
import com.oilfieldapps.allspark.strokescalculator.custom_adapters.Annulus_data_adapter;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_Data;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_DataBase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 31/08/2017.
 */

public class DSDataDisplay extends Fragment {

    public ListView ds_listView;
    private FloatingActionButton ds_add_fab;
    public Annulus_data_adapter annulus_data_adapter;
    public List<Annulus_Data> annulusDataList;
    private ConstraintLayout constraintLayout;
    private Annulus_DataBase annulus_dataBase;

    private PopupWindow ds_popupWindow;
    private Annulus_Data annulusData;

    private String diameter_chosen_units;
    private String length_chosen_units;

    public DSDataDisplay() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ds_data, container, false);

        ds_listView = view.findViewById(R.id.listView_dsData);
        ds_add_fab = view.findViewById(R.id.dsData_input_fab);
        constraintLayout = view.findViewById(R.id.ds_const_layout);
        annulus_dataBase = new Annulus_DataBase(getContext());
        annulusDataList = new ArrayList<>();
        annulusDataList = annulus_dataBase.getAllItems();

        annulus_data_adapter = new Annulus_data_adapter(getContext(), annulusDataList);

        ds_listView.setAdapter(annulus_data_adapter);

        ds_add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDS_PopUp();
            }
        });


        ds_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                TextView nameTV = view.findViewById(R.id.ds_part_name);
                TextView idTV = view.findViewById(R.id.drill_string_tv_input_ds_id);
                TextView odTV = view.findViewById(R.id.drill_string_tv_input_ds_od);
                TextView lengthTV = view.findViewById(R.id.drill_string_tv_input_ds_length);
                final String name = nameTV.getText().toString();
                String idString = idTV.getText().toString();
                String odString = odTV.getText().toString();
                String lengthString = lengthTV.getText().toString();
                final String[] oldDataString = {idString, odString, lengthString};

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
                        Log.d("NAME: ", name + " pos " + String.valueOf(position));
                        UpdateData_PopUp(position, name, oldDataString);
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        annulus_data_adapter.remove(annulusDataList.get(position));
                        annulus_dataBase.deleteFromDB(name);
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private void CreateDS_PopUp() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.popup_window_drill_string_add_new, null);

        ds_popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (Build.VERSION.SDK_INT >= 21) {
            ds_popupWindow.setElevation(10);
        }

        Button addBTN = popUpView.findViewById(R.id.drill_string_add_item_button);
        Button dismissBTN = popUpView.findViewById(R.id.drill_string_dismiss_item_button);

        final EditText ds_name = popUpView.findViewById(R.id.drill_string_et_input_name);
        final EditText ds_id = popUpView.findViewById(R.id.drill_string_et_input_ds_id);
        final EditText ds_od = popUpView.findViewById(R.id.drill_string_et_input_ds_od);
        final EditText ds_length = popUpView.findViewById(R.id.drill_string_et_input_ds_length);
        // Set proper units in PopUp window
        TextView ds_id_units = popUpView.findViewById(R.id.drill_string_et_input_ds_id_unit);
        TextView ds_od_units = popUpView.findViewById(R.id.drill_string_et_input_ds_od_unit);
        TextView ds_length_units = popUpView.findViewById(R.id.drill_string_et_input_ds_length_unit);

        //get units from preferences
        SharedPreferences unitsPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        diameter_chosen_units = unitsPreferences.getString("SNV_DIAMETER_UNITS", getResources().getString(R.string.in));
        length_chosen_units = unitsPreferences.getString("SNV_LENGTH_UNITS", getResources().getString(R.string.feet));
        ds_id_units.setText(diameter_chosen_units);
        ds_od_units.setText(diameter_chosen_units);
        ds_length_units.setText(length_chosen_units);

        ds_popupWindow.setFocusable(true);
        ds_popupWindow.update();

        ds_popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

        dismissBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds_popupWindow.dismiss();
            }
        });

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = ds_name.getText().toString();
                    String id = ds_id.getText().toString();
                    String od = ds_od.getText().toString();
                    String length = ds_length.getText().toString();
                    Log.d("TEST UNITS", diameter_chosen_units + " " + length_chosen_units);

                    annulusData = new Annulus_Data(name, id, od, length, diameter_chosen_units, length_chosen_units);
                    annulus_dataBase.addItemIntoDS_DB(annulusData);
                    annulusDataList.add(annulusData);
                    ds_listView.setAdapter(annulus_data_adapter);
                    ds_popupWindow.dismiss();
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

    public void UpdateData_PopUp(final int i, final String nameString, String[] oldData) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View updatePopUp = layoutInflater.inflate(R.layout.popup_window_drill_string_update_existing, null);

        ds_popupWindow = new PopupWindow(updatePopUp, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (Build.VERSION.SDK_INT >= 21) {
            ds_popupWindow.setElevation(10);
        }

        Button updateBTN = updatePopUp.findViewById(R.id.drill_string_update_item_button);
        Button cancelBTN = updatePopUp.findViewById(R.id.drill_string_dismiss_item_button);

        final EditText name = updatePopUp.findViewById(R.id.drill_string_et_update_name);
        final EditText id = updatePopUp.findViewById(R.id.drill_string_et_update_ds_id);
        final EditText od = updatePopUp.findViewById(R.id.drill_string_et_update_ds_od);
        final EditText length = updatePopUp.findViewById(R.id.drill_string_et_update_ds_length);

        // Set proper units in PopUp window
        TextView ds_id_units = updatePopUp.findViewById(R.id.drill_string_et_input_ds_id_unit);
        TextView ds_od_units = updatePopUp.findViewById(R.id.drill_string_et_input_ds_od_unit);
        TextView ds_length_units = updatePopUp.findViewById(R.id.drill_string_et_input_ds_length_unit);

        //get units from preferences
        SharedPreferences unitsPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        diameter_chosen_units = unitsPreferences.getString("SNV_DIAMETER_UNITS", getResources().getString(R.string.in));
        length_chosen_units = unitsPreferences.getString("SNV_LENGTH_UNITS", getResources().getString(R.string.feet));
        ds_id_units.setText(diameter_chosen_units);
        ds_od_units.setText(diameter_chosen_units);
        ds_length_units.setText(length_chosen_units);

        name.setText(nameString);
        id.setText(oldData[0]);
        od.setText(oldData[1]);
        length.setText(oldData[2]);

        ds_popupWindow.setFocusable(true);
        ds_popupWindow.update();
        ds_popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String stringName = name.getText().toString();
                    String stringID = id.getText().toString();
                    String stringOD = od.getText().toString();
                    String stringLength = length.getText().toString();

                    annulusData = new Annulus_Data(stringName, stringID, stringOD, stringLength, diameter_chosen_units, length_chosen_units);
                    annulus_dataBase.updateDB(annulusData, nameString);
                    annulusDataList.get(i).setString_name(stringName);
                    annulusDataList.get(i).setString_id(stringID);
                    annulusDataList.get(i).setString_od(stringOD);
                    annulusDataList.get(i).setString_length(stringLength);
                    ds_listView.setAdapter(annulus_data_adapter);
                    ds_popupWindow.dismiss();
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

        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds_popupWindow.dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("RESUME:", "Resuming Activity");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String diameter_units = sharedPreferences.getString("SNV_DIAMETER_UNITS", getResources().getString(R.string.in));
        String length_units = sharedPreferences.getString("SNV_LENGTH_UNITS", getResources().getString(R.string.feet));

        annulus_dataBase.updateDB_units(diameter_units, length_units);
        for (int i = 0; i < annulusDataList.size(); i++) {
            annulusDataList.get(i).setDiameter_units(diameter_units);
            annulusDataList.get(i).setLength_units(length_units);
        }
        ds_listView.setAdapter(annulus_data_adapter);

    }
}
