package com.oilfieldapps.allspark.strokescalculator.data_and_databases;

/**
 * Created by Allspark on 09/09/2017.
 */

public class HoleResultsData {

    private String name_hole_results;
    private String volume_hole_results;
    private String volume_hole_results_units;

    public HoleResultsData() {

    }

    public HoleResultsData(String _name, String _volume, String _volume_hole_results_units) {
        this.name_hole_results = _name;
        this.volume_hole_results = _volume;
        this.volume_hole_results_units = _volume_hole_results_units;
    }

    public String getName_hole_results() {
        return name_hole_results;
    }

    public String getVolume_hole_results() {
        return volume_hole_results;
    }

    public String getVolume_hole_results_units() {
        return volume_hole_results_units;
    }

    public void setName_hole_results(String name) {
        this.name_hole_results = name;
    }

    public void setVolume_hole_results(String volume) {
        this.volume_hole_results = volume;
    }

    public void setVolume_hole_results_units(String volume_hole_results_units) {
        this.volume_hole_results_units = volume_hole_results_units;
    }
}
