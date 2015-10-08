package com.caymo.simplecalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CalculatorDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Calculations3.db";
    private static final String TABLE_CALCULATIONS = "Calculations3";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CALCULATION = "calculation";

    public CalculatorDB(Context context, String name,
                        CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_CALCULATIONS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_CALCULATION + " TEXT " + ");";
        // execute SQL
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALCULATIONS);
        onCreate(db);
    }

    // add new row to the database
    public void addCalculation(Calculations Calculations) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, Calculations.getCurrentDate());
        values.put(COLUMN_CALCULATION, Calculations.getCalculation());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CALCULATIONS, null, values);
        db.close();
    }

    // delete a product from the database
    public void deleteCalculation(String date) {
        SQLiteDatabase db = getWritableDatabase(); // get reference to that database
        db.execSQL("DELETE FROM " + TABLE_CALCULATIONS + " WHERE " + COLUMN_DATE
                + " =\"" + date + "\";");
    }

    // Print out the database as a string
    public List<Calculations> databaseToString() {
        List<Calculations> calculations = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CALCULATIONS + " WHERE 1";

        // cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);

        //move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()) {
            Calculations product = cursorToProduct(c);
            calculations.add(product);
            c.moveToNext();
        }

        db.close();
        return calculations;
    }

    private Calculations cursorToProduct(Cursor c) {
        Calculations product = new Calculations();
        product.set_id(c.getInt(0));
        product.setDate((c.getString(1)));
        product.setCalculation((c.getString(2)));

        return product;
    }
}