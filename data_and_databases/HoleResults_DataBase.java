package com.oilfieldapps.allspark.strokescalculator.data_and_databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 09/09/2017.
 */

public class HoleResults_DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "Hole_results_data";
    private static final String TABLE_NAME = "hole_results_table";
    private static final String KEY_NO = "Key";
    private static final String NAME_HOLE_RESULTS = "Hole_res_name";
    private static final String VOLUME_HOLE_RESULTS = "Hole_res_volume";
    private static final String VOLUME_UNITS = "Volume_units";

    public HoleResults_DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_NO + " INTEGER PRIMARY KEY, " + NAME_HOLE_RESULTS + " TEXT, " + VOLUME_HOLE_RESULTS + " TEXT, " + VOLUME_UNITS + " TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_IF_EXITS = " DROP TABLE IF EXITS " + TABLE_NAME;
        db.execSQL(DROP_IF_EXITS);
        this.onCreate(db);

    }

    public void insertData(HoleResultsData holeResultsData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("DATABASE TEST", holeResultsData.getName_hole_results());
        values.put(NAME_HOLE_RESULTS, holeResultsData.getName_hole_results());
        values.put(VOLUME_HOLE_RESULTS, holeResultsData.getVolume_hole_results());
        values.put(VOLUME_UNITS, holeResultsData.getVolume_hole_results_units());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<HoleResultsData> getAllResults() {
        List<HoleResultsData> holeResultsDataList = new ArrayList<>();
        String select_data = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(select_data, null);
        HoleResultsData holeResultsData;
        if(cursor.moveToFirst()) {
            do {
                holeResultsData = new HoleResultsData();
                holeResultsData.setName_hole_results(cursor.getString(1));
                holeResultsData.setVolume_hole_results(cursor.getString(2));
                holeResultsData.setVolume_hole_results_units(cursor.getString(3));
                holeResultsDataList.add(holeResultsData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return holeResultsDataList;
    }

    public void deleteDatabase() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, null,null);
        sqLiteDatabase.close();
    }
}
