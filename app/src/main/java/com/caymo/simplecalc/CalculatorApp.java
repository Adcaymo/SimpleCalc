package com.caymo.simplecalc;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class CalculatorApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d("Calculator", "App started");

        // start service
        startService(new Intent(this, CalculatorService.class));
    }
}
