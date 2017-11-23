package com.oilfieldapps.allspark.strokescalculator.custom_adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 05/09/2017.
 */

public class Annulus_data_adapter extends ArrayAdapter<Annulus_Data> {

    LayoutInflater layoutInflater;
    List<Annulus_ViewHolder> annulus_viewHolders;

    public Annulus_data_adapter(Context context, List<Annulus_Data> annulusDataList) {
        super(context, 0, annulusDataList);
        layoutInflater = LayoutInflater.from(context);
        annulus_viewHolders = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Annulus_ViewHolder annulus_viewHolder = null;
        if(annulus_viewHolder == null) {
            annulus_viewHolder = new Annulus_ViewHolder();
            convertView = layoutInflater.inflate(R.layout.drill_string_list_view_data_display, parent,false);
            annulus_viewHolder.ds_name = convertView.findViewById(R.id.ds_part_name);
            annulus_viewHolder.ds_id = convertView.findViewById(R.id.drill_string_tv_input_ds_id);
            annulus_viewHolder.ds_od = convertView.findViewById(R.id.drill_string_tv_input_ds_od);
            annulus_viewHolder.ds_length = convertView.findViewById(R.id.drill_string_tv_input_ds_length);
            annulus_viewHolder.ds_id_units = convertView.findViewById(R.id.drill_string_tv_input_ds_id_unit);
            annulus_viewHolder.ds_od_units = convertView.findViewById(R.id.drill_string_tv_input_ds_od_unit);
            annulus_viewHolder.ds_length_units = convertView.findViewById(R.id.drill_string_tv_input_ds_length_unit);

            convertView.setTag(annulus_viewHolder);
            annulus_viewHolders.add(annulus_viewHolder);
        } else {
            annulus_viewHolder = (Annulus_ViewHolder) convertView.getTag();
        }

        annulus_viewHolder.setItem(getItem(position));

        return convertView;
    }
}

class Annulus_ViewHolder {

    Annulus_Data annulusData;
    TextView ds_name;
    TextView ds_id;
    TextView ds_od;
    TextView ds_length;

    TextView ds_id_units;
    TextView ds_od_units;
    TextView ds_length_units;

    public void setItem(Annulus_Data _annulusData) {
        annulusData = _annulusData;
        ds_name.setText(_annulusData.getString_name());
        ds_id.setText(_annulusData.getString_id());
        ds_od.setText(_annulusData.getString_od());
        ds_length.setText(_annulusData.getString_length());
        ds_id_units.setText(_annulusData.getDiameter_units());
        ds_od_units.setText(_annulusData.getDiameter_units());
        ds_length_units.setText(_annulusData.getLength_units());
    }

}
