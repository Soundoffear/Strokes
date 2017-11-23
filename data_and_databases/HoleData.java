package com.oilfieldapps.allspark.strokescalculator.data_and_databases;

/**
 * Created by Allspark on 02/09/2017.
 */

public class HoleData {

    String name;
    String input_id;
    String input_od;
    String input_end_md;
    String input_top_md;
    String input_diameter_unit;
    String input_length_unit;

    public HoleData() {

    }

    public HoleData(String _name, String _input_id, String _input_od, String _input_end_md, String _input_top_md, String _input_diameter_unit, String _input_length_unit) {
        this.name = _name;
        this.input_id = _input_id;
        this.input_od = _input_od;
        this.input_end_md = _input_end_md;
        this.input_top_md = _input_top_md;
        this.input_diameter_unit = _input_diameter_unit;
        this.input_length_unit = _input_length_unit;
    }

    public String getName() {
        return name;
    }

    public String getInput_id() {
        return input_id;
    }

    public String getInput_od() {
        return input_od;
    }

    public String getInput_end_md() {
        return input_end_md;
    }

    public String getInput_top_md() {
        return input_top_md;
    }

    public String getInput_diameter_unit() {
        return input_diameter_unit;
    }

    public String getInput_length_unit() {
        return input_length_unit;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setInput_id(String _input_id) {
        this.input_id = _input_id;
    }

    public void setInput_od(String _input_od) {
        this.input_od = _input_od;
    }

    public void setInput_end_md(String _input_end_md) {
        this.input_end_md = _input_end_md;
    }

    public void setInput_top_md(String _input_top_md) {
        this.input_top_md = _input_top_md;
    }

    public void setInput_diameter_unit(String input_diameter_unit) {
        this.input_diameter_unit = input_diameter_unit;
    }

    public void setInput_length_unit(String input_length_unit) {
        this.input_length_unit = input_length_unit;
    }
}
