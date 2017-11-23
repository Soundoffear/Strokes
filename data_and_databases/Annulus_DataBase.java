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

public class Annulus_DataBase extends SQLiteOpenHelper {

    private static final int DS_DB_VERSION = 1;
    private static final String DS_DB_NAME = "DrillString_DB";
    private static final String DS_TABLE_NAME = "DrillString_Table";
    private static final String KEY_ID = "Key_ID";
    private static final String DS_NAME = "DS_name";
    private static final String DS_ID = "DS_ID";
    private static final String DS_OD = "DS_OD";
    private static final String DS_LENGTH = "DS_LENGTH";
    private static final String DS_DIA_UNITS = "DS_DIA_UNITS";
    private static final String DS_LENGTH_UNITS = "DS_LENGTH_UNITS";

    public Annulus_DataBase(Context context) {
        super(context, DS_DB_NAME, null, DS_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + DS_TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + DS_NAME + " TEXT, " +
                DS_ID + " TEXT, " + DS_OD + " TEXT, " + DS_LENGTH + " TEXT, " + DS_DIA_UNITS + " TEXT, " + DS_LENGTH_UNITS + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_IF_EXITS = "DROP TABLE IF EXISTS " + DS_TABLE_NAME;
        db.execSQL(DROP_IF_EXITS);
        this.onCreate(db);
    }

    public void addItemIntoDS_DB(Annulus_Data annulusData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DS_NAME, annulusData.getString_name());
        contentValues.put(DS_ID, annulusData.getString_id());
        contentValues.put(DS_OD, annulusData.getString_od());
        contentValues.put(DS_LENGTH, annulusData.getString_length());
        contentValues.put(DS_DIA_UNITS, annulusData.getDiameter_units());
        contentValues.put(DS_LENGTH_UNITS, annulusData.getLength_units());
        db.insert(DS_TABLE_NAME, null, contentValues);
        db.close();
    }

    public List<Annulus_Data> getAllItems() {
        List<Annulus_Data> annulusDataList = new ArrayList<>();

        String selectData = "SELECT * FROM " + DS_TABLE_NAME;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(selectData, null);
        Annulus_Data annulusData;
        if(cursor.moveToFirst()) {
            do {
                annulusData = new Annulus_Data();
                annulusData.setString_name(cursor.getString(1));
                annulusData.setString_id(cursor.getString(2));
                annulusData.setString_od(cursor.getString(3));
                annulusData.setString_length(cursor.getString(4));
                annulusData.setDiameter_units(cursor.getString(5));
                annulusData.setLength_units(cursor.getString(6));
                annulusDataList.add(annulusData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return annulusDataList;
    }

    public void deleteFromDB(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.execSQL("DELETE FROM " + DS_TABLE_NAME + " WHERE " + DS_NAME + "='" + name + "'");
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void deleteDB() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DS_TABLE_NAME, null, null);
        db.close();
    }

    public void updateDB(Annulus_Data annulusData, String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DS_NAME, annulusData.getString_name());
        contentValues.put(DS_ID, annulusData.getString_id());
        contentValues.put(DS_OD, annulusData.getString_od());
        contentValues.put(DS_LENGTH, annulusData.getString_length());
        String where = DS_NAME + " = '" + name + "'";
        db.beginTransaction();
        db.update(DS_TABLE_NAME, contentValues, where, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void updateDB_units(String diameter_units, String length_units) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DS_DIA_UNITS, diameter_units);
        contentValues.put(DS_LENGTH_UNITS, length_units);
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.update(DS_TABLE_NAME, contentValues, null, null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.close();
    }

}
