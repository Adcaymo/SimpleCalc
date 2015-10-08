package com.caymo.simplecalc;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Calculations {

    private int _id;
    private String date = "";
    private String calculation;

    private SimpleDateFormat dateOutFormat =
            new SimpleDateFormat("MM/dd/yy HH:mm:ss");

    private SimpleDateFormat dateInFormat =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

    public Calculations() {
    }

    public Calculations(String calculation) {
        this.calculation = calculation;
    }

    public Calculations(String date, String calculation) {
        this.date = date;
        this.calculation = calculation;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public String getCurrentDate() {
        Date date = new Date();
        return date.toString();
    }

    public String getCalcDateFormatted() {
        getCurrentDate();

        String calcDateFormatted = "";
        try {
            Date calculationDate = dateInFormat.parse(date.trim());
            calcDateFormatted = dateOutFormat.format(calculationDate);
            return calcDateFormatted;
        }
        catch (ParseException e) {
            return calcDateFormatted;
        }
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }
}
