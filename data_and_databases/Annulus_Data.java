package com.oilfieldapps.allspark.strokescalculator.data_and_databases;

/**
 * Created by Allspark on 05/09/2017.
 */

public class Annulus_Data {

    private String string_name;
    private String string_id;
    private String string_od;
    private String string_length;
    private String diameter_units;
    private String length_units;

    public Annulus_Data() {

    }

    public Annulus_Data(String _string_name, String _string_id, String _string_od, String _string_length, String _diameter_units, String _length_units) {
        this.string_name = _string_name;
        this.string_id = _string_id;
        this.string_od = _string_od;
        this.string_length = _string_length;
        this.diameter_units = _diameter_units;
        this.length_units = _length_units;
    }

    public String getString_name() {
        return string_name;
    }

    public String getString_id() {
        return string_id;
    }

    public String getString_od() {
        return string_od;
    }

    public String getString_length() {
        return string_length;
    }

    public void setString_name(String string_name) {
        this.string_name = string_name;
    }

    public void setString_id(String string_id) {
        this.string_id = string_id;
    }

    public void setString_od(String string_od) {
        this.string_od = string_od;
    }

    public void setString_length(String string_length) {
        this.string_length = string_length;
    }

    public String getDiameter_units() {
        return diameter_units;
    }

    public String getLength_units() {
        return length_units;
    }

    public void setDiameter_units(String diameter_units) {
        this.diameter_units = diameter_units;
    }

    public void setLength_units(String length_units) {
        this.length_units = length_units;
    }
}
