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

public class Annulus_Results_DataBase extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DrillString_results";
    private static final String TABLE_NAME = "DrillString_table";
    private static final String KEY_ID = "Key_id";
    private static final String DS_PART_NAME = "DS_part_name";
    private static final String DS_VOLUME = "DS_volume";
    private static final String DS_STROKES = "DS_strokes";
    private static final String DS_VOL_UNITS = "DS_volume_units";

    public Annulus_Results_DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + DS_PART_NAME + " TEXT, " + DS_VOLUME + " TEXT, " + DS_STROKES + " TEXT, " + DS_VOL_UNITS + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_IF_EXISTS = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_IF_EXISTS);
        this.onCreate(db);
    }

    public void inputData(Annulus_Results annulusResults) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DS_PART_NAME, annulusResults.getDs_part_name());
        values.put(DS_VOLUME, annulusResults.getDs_part_volume());
        values.put(DS_STROKES, annulusResults.getDs_part_strokes());
        values.put(DS_VOL_UNITS, annulusResults.getDs_part_volume_units());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Annulus_Results> getAllItem() {
        List<Annulus_Results> annulusResultses = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        Annulus_Results annulusResults;
        if(cursor.moveToFirst()) {
            do {
                annulusResults = new Annulus_Results();
                annulusResults.setDs_part_name(cursor.getString(1));
                annulusResults.setDs_part_volume(cursor.getString(2));
                annulusResults.setDs_part_strokes(cursor.getString(3));
                annulusResults.setDs_part_volume_units(cursor.getString(4));
                annulusResultses.add(annulusResults);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return annulusResultses;
    }

    public void delateDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
