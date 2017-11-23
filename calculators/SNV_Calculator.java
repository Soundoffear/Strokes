package com.oilfieldapps.allspark.strokescalculator.calculators;

import java.util.ArrayList;

/**
 * Created by Allspark on 17/07/2017.
 */

public class SNV_Calculator {

    public SNV_Calculator () {

    }

    private static ArrayList<Double> results_Empty_Volume;
    private static ArrayList<Double> results_Annular_Volumes;
    private static ArrayList<Double> resutls_DS_Volume;

    public static void EmptyVolume (ArrayList<Double> d_depths_not_ds, ArrayList<Double> d_ids_not_ds) {
        for(int i = 0; i < d_depths_not_ds.size(); i++) {
            if(i == 0) {
                double tempVol = Math.pow(d_ids_not_ds.get(i), 2) / 1029.4 * d_depths_not_ds.get(i);
                results_Empty_Volume.add(tempVol);
            } else {
                double tempLength = d_depths_not_ds.get(i) - d_depths_not_ds.get(i-1);
                double tempVol = Math.pow(d_ids_not_ds.get(i), 2) / 1029.4 * tempLength;
                results_Empty_Volume.add(tempVol);
            }
        }
    }

    public static void AnnularVolume(ArrayList<Double> d_depth_not_ds, ArrayList<Double> d_ids_not_ds, double ds_depth, double ds_id) {
        for(int i = 0; i < d_depth_not_ds.size();i++) {
            if(ds_depth >= d_depth_not_ds.get(i)) {
                if(i == 0) {
                    double tempVol = (Math.pow(d_ids_not_ds.get(i), 2) - Math.pow(ds_id, 2)) / 1029.4 * d_depth_not_ds.get(i);
                    results_Annular_Volumes.add(tempVol);
                } else {
                    double tempLength = d_depth_not_ds.get(i) - d_depth_not_ds.get(i-1);
                    double tempVol = (Math.pow(d_ids_not_ds.get(i),2) - Math.pow(ds_id,2)) / 1029.4 * tempLength;
                    results_Annular_Volumes.add(tempVol);
                }
            } else if(ds_depth < d_depth_not_ds.get(i)) {
                double tempVol = (Math.pow(d_ids_not_ds.get(i), 2) - Math.pow(ds_id, 2)) / 1029.4 * (d_depth_not_ds.get(i) - ds_depth);
                results_Annular_Volumes.add(tempVol);
            }
        }
    }

}
