package com.caymo.simplecalc;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class CalculatorApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // start service
        startService(new Intent(this, CalculatorService.class));
    }
}
