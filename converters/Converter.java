package com.oilfieldapps.allspark.strokescalculator.converters;

/**
 * Created by Allspark on 15/07/2017.
 */

public class Converter {

    public Converter() {

    }

    public double diameterConverter (String oldUnit, String newUnit, double value) {
        switch (oldUnit) {
            case "in": switch (newUnit) {
                case "mm":
                    return value * 25.4;
                case "cm":
                    return value * 2.54;
                default:
                    return value;
            }
            case "mm": switch (newUnit) {
                case "in":
                    return value * 0.03937;
                case "cm":
                    return value * 0.1;
                default:
                    return value;
            }
            case "cm": switch (newUnit) {
                case "in":
                    return value * 0.393701;
                case "mm":
                    return value * 10;
                default:
                    return value;
            }
            default:
                return value;
        }
    }

    public double VolumeConverter(String oldVolumeUnit, String newVolumeUnit, double value) {
        double finalVolValue;
        switch (oldVolumeUnit) {
            case "bbl":
                switch (newVolumeUnit) {
                    case "gal":
                        finalVolValue = value * 42.000001339881;
                        return finalVolValue;
                    case "ft3":
                        finalVolValue = value * 5.6145835124493;
                        return finalVolValue;
                    case "m3":
                        finalVolValue = value * 0.1589873;
                        return finalVolValue;
                    case "l":
                        finalVolValue = value * 158.9873;
                        return finalVolValue;
                    case "in3":
                        finalVolValue = value * 9702.0003095124;
                        return finalVolValue;
                    default:
                        return value;
                }
            case "gal":
                switch (newVolumeUnit) {
                    case "bbl":
                        finalVolValue = value * 0.023809523049954;
                        return finalVolValue;
                    case "ft3":
                        finalVolValue = value * 0.13368055555556;
                        return finalVolValue;
                    case "m3":
                        finalVolValue = value * 0.003785411784;
                        return finalVolValue;
                    case "l":
                        finalVolValue = value * 3.785411784;
                        return finalVolValue;
                    case "in3":
                        finalVolValue = value * 231;
                        return finalVolValue;
                    default:
                        return value;
                }
            case "ft3":
                switch (newVolumeUnit) {
                    case "gal":
                        finalVolValue = value * 7.4805194805195;
                        return finalVolValue;
                    case "bbl":
                        finalVolValue = value * 0.17810760099706;
                        return finalVolValue;
                    case "m3":
                        finalVolValue = value * 0.028316846592;
                        return finalVolValue;
                    case "l":
                        finalVolValue = value * 28.316846592;
                        return finalVolValue;
                    case "in3":
                        finalVolValue = value * 1728;
                        return finalVolValue;
                    default:
                        return value;
                }
            case "m3":
                switch (newVolumeUnit) {
                    case "gal":
                        finalVolValue = value * 264.17205235815;
                        return finalVolValue;
                    case "ft3":
                        finalVolValue = value * 35.314666721489;
                        return finalVolValue;
                    case "bbl":
                        finalVolValue = value * 6.2898105697751;
                        return finalVolValue;
                    case "l":
                        finalVolValue = value * 1000;
                        return finalVolValue;
                    case "m3":
                        finalVolValue = value * 61023.744094732;
                        return finalVolValue;
                    default:
                        return value;
                }
            case "l":
                switch (newVolumeUnit) {
                    case "gal":
                        finalVolValue = value * 0.26417205235815;
                        return finalVolValue;
                    case "ft3":
                        finalVolValue = value * 0.035314666721489;
                        return finalVolValue;
                    case "m3":
                        finalVolValue = value * 0.001;
                        return finalVolValue;
                    case "bbl":
                        finalVolValue = value * 0.0062898105697751;
                        return finalVolValue;
                    case "in3":
                        finalVolValue = value * 61.023744094732;
                        return finalVolValue;
                    default:
                        return value;
                }
            case "in3":{
                switch (newVolumeUnit) {
                    case "gal":
                        finalVolValue = value * 0.0043290043290043;
                        return finalVolValue;
                    case "ft3":
                        finalVolValue = value * 0.0005787037037037;
                        return finalVolValue;
                    case "bbl":
                        finalVolValue = value * 0.00010307152835478;
                        return finalVolValue;
                    case "m3":
                        finalVolValue = value * 0.000016387064;
                        return finalVolValue;
                    case "l":
                        finalVolValue = value * 0.016387064;
                        return finalVolValue;
                    default:
                        return value;
                }
            }
            default:
                return value;
        }
    }

    public double lengthConverter (String oldUnit, String newUnit, double value) {
        switch (oldUnit) {
            case "feet": switch (newUnit) {
                case "m":
                    return value * 0.3048;
                default:
                    return value;
            }
            case "m": switch (newUnit) {
                case "feet":
                    return value * 3.2808398950131;
                default:
                    return value;
            }
            default:
                return value;
        }
    }

    public double outputConverter (String oldUnit, String newUnit, double value) {
        switch (oldUnit) {
            case "bbl/stk":
                switch (newUnit) {
                    case "gal/stk":
                        return value * 42.000001339881;
                    case "l/stk":
                        return value * 158.9873;
                    case "m3/stk":
                        return value * 0.1589873;
                    default:
                        return value;
                }
            case "gal/stk":
                switch (newUnit) {
                    case "bbl/stk":
                        return value * 0.023809523049954;
                    case "l/stk":
                        return value * 3.785411784;
                    case "m3/stk":
                        return value * 0.003785411784;
                    default:
                        return value;
                }
            case "l/stk":
                switch (newUnit) {
                    case "bbl/stk":
                        return value * 0.0062898105697751;
                    case "gal/stk":
                        return value * 0.26417205235815;
                    case "m3/stk":
                        return value * 0.001;
                    default:
                        return value;
                }
            case "m3/stk":
                switch (newUnit) {
                    case "bbl/stk":
                        return value * 6.2898105697751;
                    case "gal/stk":
                        return value * 264.17205235815;
                    case "l/stk":
                        return value * 1000;
                    default:
                        return value;
                }
            default:
                return value;
        }
    }

    public double annularVelocityConverter(String oldUnit, String newUnit, double value) {

        switch (oldUnit) {
            case "ft/min":
                switch (newUnit) {
                    case "m/s":
                        return value * 0.00508;
                    case "m/min":
                        return value * 0.3048;
                    case "ft/s":
                        return value * 0.0166666667;
                    default:
                        return value;
                }
            case "ft/s":
                switch (newUnit) {
                    case "m/s":
                        return value * 0.3048;
                    case "m/min":
                        return value * 18.288;
                    case "ft/min":
                        return value * 60;
                    default:
                        return value;
                }
            case "m/s":
                switch (newUnit) {
                    case "ft/min":
                        return value * 196.850394;
                    case "ft/s":
                        return value * 3.28084;
                    case "m/min":
                        return value * 60;
                    default:
                        return value;
                }
            case "m/min":
                switch (newUnit) {
                    case "m/s":
                        return value / 60;
                    case "ft/s":
                        return value * 0.054681;
                    case "ft/min":
                        return value * 3.28084;
                    default:
                        return value;
                }
            default:
                return value;
        }
    }
}
