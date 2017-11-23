package com.oilfieldapps.allspark.strokescalculator.data_and_databases;

/**
 * Created by Allspark on 10/09/2017.
 */

public class DrillString_Results {

    private String drillString_name;
    private String drillString_volume;
    private String drillString_strokes;
    private String drillString_volume_units;

    public DrillString_Results() {

    }

    public DrillString_Results(String _drillString_name, String _drillString_volume, String _drillString_strokes, String _drillString_volume_units) {
        this.drillString_name = _drillString_name;
        this.drillString_volume = _drillString_volume;
        this.drillString_strokes = _drillString_strokes;
        this.drillString_volume_units = _drillString_volume_units;
    }

    public String getDrillString_name() {
        return drillString_name;
    }

    public String getDrillString_volume() {
        return drillString_volume;
    }

    public String getDrillString_strokes() {
        return drillString_strokes;
    }

    public String getDrillString_volume_units() {
        return drillString_volume_units;
    }

    public void setDrillString_name(String drillString_name) {
        this.drillString_name = drillString_name;
    }

    public void setDrillString_volume(String drillString_volume) {
        this.drillString_volume = drillString_volume;
    }

    public void setDrillString_strokes(String drillString_strokes) {
        this.drillString_strokes = drillString_strokes;
    }

    public void setDrillString_volume_units(String drillString_volume_units) {
        this.drillString_volume_units = drillString_volume_units;
    }
}
