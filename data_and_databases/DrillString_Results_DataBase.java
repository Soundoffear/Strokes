package com.oilfieldapps.allspark.strokescalculator.data_and_databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 10/09/2017.
 */

public class DrillString_Results_DataBase extends SQLiteOpenHelper {

    private static final int DS_DB_VER = 1;
    private static final String DS_DB_NAME = "DS_DB";
    private static final String DS_TABLE_NAME = "DS_TABLE_NAME";
    private static final String DS_KEY_ID = "DS_KEY";
    private static final String DS_PART_NAME = "DS_PART_NAME";
    private static final String DS_PART_VOLUME = "DS_PART_VOLUME";
    private static final String DS_PART_STROKES = "DS_PART_STROKES";
    private static final String DS_UNITS_VOLUME = "DS_VOLUME_UNITS";

    public DrillString_Results_DataBase(Context context) {
        super(context, DS_DB_NAME, null, DS_DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + DS_TABLE_NAME + "(" + DS_KEY_ID + " INTEGER PRIMARY KEY, " + DS_PART_NAME + " TEXT, " + DS_PART_VOLUME + " TEXT, " + DS_PART_STROKES + " TEXT, " + DS_UNITS_VOLUME + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_IF_EXITS = "DROP TABLE IF EXISTS " + DS_TABLE_NAME;
        db.execSQL(DROP_IF_EXITS);
        this.onCreate(db);
    }

    public void inputItem(DrillString_Results drillString_results) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DS_PART_NAME, drillString_results.getDrillString_name());
        values.put(DS_PART_VOLUME, drillString_results.getDrillString_volume());
        values.put(DS_PART_STROKES, drillString_results.getDrillString_strokes());
        values.put(DS_UNITS_VOLUME, drillString_results.getDrillString_volume_units());
        db.insert(DS_TABLE_NAME, null, values);
        db.close();
    }

    public List<DrillString_Results> getAllItems() {
        List<DrillString_Results> drillString_resultsList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DS_TABLE_NAME, null);
        DrillString_Results drillString_results;
        if(cursor.moveToFirst()) {
            do {
                drillString_results = new DrillString_Results();
                drillString_results.setDrillString_name(cursor.getString(1));
                drillString_results.setDrillString_volume(cursor.getString(2));
                drillString_results.setDrillString_strokes(cursor.getString(3));
                drillString_results.setDrillString_volume_units(cursor.getString(4));
                drillString_resultsList.add(drillString_results);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return drillString_resultsList;
    }

    public void deleteDB() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DS_TABLE_NAME, null, null);
        db.close();
    }
}
