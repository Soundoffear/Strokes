package com.oilfieldapps.allspark.strokescalculator.custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.DrillString_Results;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 10/09/2017.
 */

public class DrillString_results_adapter extends ArrayAdapter<DrillString_Results> {

    LayoutInflater layoutInflater;
    List<DrillString_results_viewHolder> drillString_view_holder_list;

    public DrillString_results_adapter(Context context, List<DrillString_Results> drillString_results) {
        super(context, 0, drillString_results);
        layoutInflater = LayoutInflater.from(context);
        drillString_view_holder_list = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrillString_results_viewHolder drillString_results_viewHolder = null;
        if(drillString_results_viewHolder == null) {
            drillString_results_viewHolder = new DrillString_results_viewHolder();
            convertView = layoutInflater.inflate(R.layout.s_and_v_data_out_drill_string_list_view, null);
            drillString_results_viewHolder.ds_name_results = (TextView) convertView.findViewById(R.id.drill_string_results_name);
            drillString_results_viewHolder.ds_volume_results = (TextView) convertView.findViewById(R.id.drill_string_results_volume);
            drillString_results_viewHolder.ds_strokes_results = (TextView) convertView.findViewById(R.id.drill_string_results_strokes);
            drillString_results_viewHolder.ds_volume_results_units = (TextView) convertView.findViewById(R.id.drill_string_results_volume_units);
            convertView.setTag(drillString_results_viewHolder);
            drillString_view_holder_list.add(drillString_results_viewHolder);
        } else {
            drillString_results_viewHolder = (DrillString_results_viewHolder) convertView.getTag();
        }

        drillString_results_viewHolder.setData(getItem(position));

        return convertView;
    }
}

class DrillString_results_viewHolder {

    DrillString_Results drillString_results;
    TextView ds_name_results;
    TextView ds_volume_results;
    TextView ds_strokes_results;
    TextView ds_volume_results_units;

    public void setData(DrillString_Results _drillString_results) {
        this.drillString_results = _drillString_results;
        ds_name_results.setText(_drillString_results.getDrillString_name());
        ds_volume_results.setText(_drillString_results.getDrillString_volume());
        ds_strokes_results.setText(_drillString_results.getDrillString_strokes());
        ds_volume_results_units.setText(_drillString_results.getDrillString_volume_units());
    }

}
