package com.caymo.simplecalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;
import android.util.Log;

public class PastCalculationsActivity extends AppCompatActivity {
    private TextView pastCalculationTextView;
    private CalculatorDB calculatorDB;
    public static final String TAG = "PastCalcActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_calculations);

        pastCalculationTextView = (TextView) findViewById(R.id.pastCalculationTextView);

        calculatorDB = new CalculatorDB(this, null, null, 1);
        List<Calculations> dbString = calculatorDB.databaseToString();
        String calculation ="";
        for (Calculations c : dbString) {
            calculation += "Date: " + c.getCalcDateFormatted()
                    + ", Calculation: " + c.getCalculation() + "\n";
        }
        pastCalculationTextView.setText(calculation);
    }
}
