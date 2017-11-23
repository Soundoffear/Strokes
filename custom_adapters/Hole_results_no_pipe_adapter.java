package com.oilfieldapps.allspark.strokescalculator.custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleResultsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 09/09/2017.
 */

public class Hole_results_no_pipe_adapter extends ArrayAdapter<HoleResultsData> {

    LayoutInflater layoutInflater;
    List<Hole_results_no_pipe_view_holder> hole_results_no_pipe_view_holders;

    public Hole_results_no_pipe_adapter(Context context, List<HoleResultsData> holeResultsDatas) {
        super(context, 0 , holeResultsDatas);
        layoutInflater = LayoutInflater.from(context);
        hole_results_no_pipe_view_holders = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hole_results_no_pipe_view_holder hole_results_no_pipe_view_holder = null;
        if(hole_results_no_pipe_view_holder == null) {
            hole_results_no_pipe_view_holder = new Hole_results_no_pipe_view_holder();
            convertView = layoutInflater.inflate(R.layout.s_and_v_data_out_empty_hole_list_view, null);
            hole_results_no_pipe_view_holder.name_results_TV = (TextView) convertView.findViewById(R.id.list_view_hole_results_no_pipe_name);
            hole_results_no_pipe_view_holder.volume_results_TV = (TextView) convertView.findViewById(R.id.hole_result_no_pipe_volume);
            hole_results_no_pipe_view_holder.volume_units_results_TV = (TextView) convertView.findViewById(R.id.hole_result_no_pipe_volume_units);
            convertView.setTag(hole_results_no_pipe_view_holder);
            hole_results_no_pipe_view_holders.add(hole_results_no_pipe_view_holder);
        } else {
            hole_results_no_pipe_view_holder = (Hole_results_no_pipe_view_holder) convertView.getTag();
        }

        hole_results_no_pipe_view_holder.setData(getItem(position));

        return convertView;
    }
}

class Hole_results_no_pipe_view_holder {

    HoleResultsData holeResultsData;
    TextView name_results_TV;
    TextView volume_results_TV;
    TextView volume_units_results_TV;

    public void setData (HoleResultsData _holeResultsData) {
        this.holeResultsData = _holeResultsData;
        name_results_TV.setText(_holeResultsData.getName_hole_results());
        volume_results_TV.setText(_holeResultsData.getVolume_hole_results());
        volume_units_results_TV.setText(_holeResultsData.getVolume_hole_results_units());
    }

}
