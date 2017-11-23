package com.oilfieldapps.allspark.strokescalculator.custom_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.Annulus_Results;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 10/09/2017.
 */

public class Annulus_results_adapter extends ArrayAdapter<Annulus_Results> {

    LayoutInflater layoutInflater;
    List<Annulus_Results_ViewHolder> annulusResults_viewHolders;

    public Annulus_results_adapter(Context context, List<Annulus_Results> annulusResultses) {
        super(context, 0, annulusResultses);
        layoutInflater = LayoutInflater.from(context);
        annulusResults_viewHolders = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Annulus_Results_ViewHolder annulusResults_viewHolder = null;

        if(annulusResults_viewHolder ==null) {
            annulusResults_viewHolder = new Annulus_Results_ViewHolder();
            convertView = layoutInflater.inflate(R.layout.s_and_v_data_out_annular_list_view, null);
            annulusResults_viewHolder.ds_tv_name = (TextView) convertView.findViewById(R.id.list_view_ds_results_name);
            annulusResults_viewHolder.ds_tv_volume = (TextView) convertView.findViewById(R.id.ds_result_volume);
            annulusResults_viewHolder.ds_tv_strokes = (TextView) convertView.findViewById(R.id.ds_result_strokes);
            annulusResults_viewHolder.ds_tv_volume_units = (TextView) convertView.findViewById(R.id.ds_result_volume_units);
            convertView.setTag(annulusResults_viewHolder);
            annulusResults_viewHolders.add(annulusResults_viewHolder);
        } else {
            annulusResults_viewHolder = (Annulus_Results_ViewHolder) convertView.getTag();
        }

        annulusResults_viewHolder.setData(getItem(position));

        return convertView;
    }
}

class Annulus_Results_ViewHolder {
    Annulus_Results annulusResults;
    TextView ds_tv_name;
    TextView ds_tv_volume;
    TextView ds_tv_strokes;
    TextView ds_tv_volume_units;

    public void setData(Annulus_Results _annulusResults) {
        this.annulusResults = _annulusResults;
        ds_tv_name.setText(_annulusResults.getDs_part_name());
        ds_tv_volume.setText(_annulusResults.getDs_part_volume());
        ds_tv_strokes.setText(_annulusResults.getDs_part_strokes());
        ds_tv_volume_units.setText(_annulusResults.getDs_part_volume_units());
    }
}
