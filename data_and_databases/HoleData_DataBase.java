package com.oilfieldapps.allspark.strokescalculator.data_and_databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allspark on 05/09/2017.
 */

public class HoleData_DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Hole_DataBase";
    private static final String TABLE_NAME = "HoleData";
    private static final String KEY_ID = "KEY_ID";
    private static final String NAME = "Name";
    private static final String TOP_MD = "Top_MD";
    private static final String END_MD = "End_MD";
    private static final String S_OD = "S_OD";
    private static final String S_ID = "S_ID";
    private static final String DIAMETER_UNITS = "Diameter_units";
    private static final String LENGTH_UNITS = "Length_units";

    public HoleData_DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT, " + TOP_MD + " TEXT, " + END_MD + " TEXT, " + S_OD + " TEXT, " + S_ID + " TEXT," + DIAMETER_UNITS + " TEXT, " + LENGTH_UNITS + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_IF_EXITS = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_IF_EXITS);
        this.onCreate(db);
    }

    public void addItemToHoleDesign(HoleData holeData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues holeDataInput = new ContentValues();
        holeDataInput.put(NAME, holeData.getName());
        holeDataInput.put(TOP_MD, holeData.getInput_top_md());
        holeDataInput.put(END_MD, holeData.getInput_end_md());
        holeDataInput.put(S_OD, holeData.getInput_od());
        holeDataInput.put(S_ID, holeData.getInput_id());
        holeDataInput.put(DIAMETER_UNITS, holeData.getInput_diameter_unit());
        holeDataInput.put(LENGTH_UNITS, holeData.getInput_length_unit());
        db.insert(TABLE_NAME, null, holeDataInput);
        db.close();
    }

    public List<HoleData> getAllItem() {
        List<HoleData> holeDataList = new ArrayList<>();
        String select_items_from_db = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(select_items_from_db, null);
        HoleData holeData;
        if(cursor.moveToFirst()) {
            do {
                holeData = new HoleData();
                holeData.setName(cursor.getString(1));
                holeData.setInput_top_md(cursor.getString(2));
                holeData.setInput_end_md(cursor.getString(3));
                holeData.setInput_od(cursor.getString(4));
                holeData.setInput_id(cursor.getString(5));
                holeData.setInput_diameter_unit(cursor.getString(6));
                holeData.setInput_length_unit(cursor.getString(7));
                holeDataList.add(holeData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return holeDataList;
    }

    public void removeFromDatabase(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + NAME + " = '" + name + "'");
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void deleteDB() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void updateDatabase(HoleData holeData, String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValue = new ContentValues();
        newValue.put(NAME, holeData.getName());
        newValue.put(TOP_MD, holeData.getInput_top_md());
        newValue.put(END_MD, holeData.getInput_end_md());
        newValue.put(S_OD, holeData.getInput_od());
        newValue.put(S_ID, holeData.getInput_id());

        String where = NAME + " = '" + name + "'";
        db.beginTransaction();
        db.update(TABLE_NAME, newValue, where, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    public void updateUnits(String diameterUnits, String lengthUnits) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues newUnits = new ContentValues();
        newUnits.put(DIAMETER_UNITS, diameterUnits);
        newUnits.put(LENGTH_UNITS, lengthUnits);
        db.beginTransaction();
        db.update(TABLE_NAME, newUnits, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }
}
