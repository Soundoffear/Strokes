package com.oilfieldapps.allspark.strokescalculator.custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oilfieldapps.allspark.strokescalculator.R;
import com.oilfieldapps.allspark.strokescalculator.data_and_databases.HoleData;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * Created by Allspark on 02/09/2017.
 */

public class Hole_data_adapter extends ArrayAdapter<HoleData> {

    private LayoutInflater layoutInflater;
    private List<ViewHolder> viewHolderList;

    public Hole_data_adapter(Context context, List<HoleData> holeDataList) {
        super(context, 0, holeDataList);
        layoutInflater = LayoutInflater.from(context);
        viewHolderList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_view_data_display, parent, false);

            viewHolder.inputName = (TextView) convertView.findViewById(R.id.itemNameLV);
            viewHolder.inputID = (TextView) convertView.findViewById(R.id.input_ID_LV_ET);
            viewHolder.inputOD = (TextView) convertView.findViewById(R.id.input_OD_LV_ET);
            viewHolder.input_EndMD = (TextView) convertView.findViewById(R.id.input_end_md_TV);
            viewHolder.input_TopMD = (TextView) convertView.findViewById(R.id.input_top_md_TV);
            viewHolder.unitID = (TextView) convertView.findViewById(R.id.input_ID_LV_TV_units);
            viewHolder.unitOD = (TextView) convertView.findViewById(R.id.input_OD_LV_ET_units);
            viewHolder.unitEnd_MD = (TextView) convertView.findViewById(R.id.input_end_md_TV_units);
            viewHolder.unitTop_MD = (TextView) convertView.findViewById(R.id.input_top_md_TV_units);

            convertView.setTag(viewHolder);
            viewHolderList.add(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.setItem(getItem(position));

        return convertView;
    }
}

class ViewHolder {

    HoleData holeData;
    TextView inputName;
    TextView inputID;
    TextView inputOD;
    TextView input_EndMD;
    TextView input_TopMD;
    //Units
    TextView unitID;
    TextView unitOD;
    TextView unitEnd_MD;
    TextView unitTop_MD;

    public void setItem(HoleData _holeData) {
        holeData = _holeData;
        inputName.setText(_holeData.getName());
        inputID.setText(_holeData.getInput_id());
        inputOD.setText(_holeData.getInput_od());
        input_EndMD.setText(_holeData.getInput_end_md());
        input_TopMD.setText(_holeData.getInput_top_md());

        unitID.setText(_holeData.getInput_diameter_unit());
        unitOD.setText(_holeData.getInput_diameter_unit());
        unitEnd_MD.setText(_holeData.getInput_length_unit());
        unitTop_MD.setText(_holeData.getInput_length_unit());
    }

}
