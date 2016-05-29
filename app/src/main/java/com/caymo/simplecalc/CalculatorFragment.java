package com.caymo.simplecalc;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.util.List;

public class CalculatorFragment extends Fragment
        implements OnClickListener {

    // variables for widgets
    private TextView displayTextView;
    private TextView realTimeTextView;
    private Button acButton, plusMinusButton, percentButton, divisionButton,
            sevenButton, eightButton, nineButton, multiplicationButton,
            fourButton, fiveButton, sixButton, subtractionButton,
            oneButton, twoButton, threeButton, additionButton,
            doubleZeroButton, zeroButton, decimalButton, equalsButton;
    private TextView pastCalculationsTitleTextView;
    private TextView pastCalculationTextView;

    private int operator;
    private boolean clearScreen = false;
    private boolean hasChanged = false;
    private double num = 0;

    // variables for preferences
    private SharedPreferences prefs;
    private int theme;
    private String displayString;
    private Calculations calculations;
    private CalculatorDB calculatorDB;

    public static final String TAG = "CalculatorFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(getActivity(),
                R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setHasOptionsMenu(true);

        calculatorDB = new CalculatorDB(getActivity(), null, null, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate layout for fragment
        View view = inflater.inflate(R.layout.fragment_calculator,
                container, false);

        // references to widgets
        displayTextView = (TextView) view.findViewById(R.id.displayTextView);
        acButton = (Button) view.findViewById(R.id.acButton);
        plusMinusButton = (Button) view.findViewById(R.id.plusMinusButton);
        percentButton = (Button) view.findViewById(R.id.percentButton);
        divisionButton = (Button) view.findViewById(R.id.divisionButton);
        sevenButton = (Button) view.findViewById(R.id.sevenButton);
        eightButton = (Button) view.findViewById(R.id.eightButton);
        nineButton = (Button) view.findViewById(R.id.nineButton);
        multiplicationButton = (Button) view.findViewById(R.id.multiplicationButton);
        fourButton = (Button) view.findViewById(R.id.fourButton);
        fiveButton = (Button) view.findViewById(R.id.fiveButton);
        sixButton = (Button) view.findViewById(R.id.sixButton);
        subtractionButton = (Button) view.findViewById(R.id.subtractionButton);
        oneButton = (Button) view.findViewById(R.id.oneButton);
        twoButton = (Button) view.findViewById(R.id.twoButton);
        threeButton = (Button) view.findViewById(R.id.threeButton);
        additionButton = (Button) view.findViewById(R.id.additionButton);
        doubleZeroButton = (Button) view.findViewById(R.id.deleteButton);
        zeroButton = (Button) view.findViewById(R.id.zeroButton);
        decimalButton = (Button) view.findViewById(R.id.decimalButton);
        equalsButton = (Button) view.findViewById(R.id.equalsButton);
        pastCalculationTextView = (TextView) view.findViewById(R.id.pastCalculationTextView);

        // listeners set
        acButton.setOnClickListener(this);
        plusMinusButton.setOnClickListener(this);
        percentButton.setOnClickListener(this);
        divisionButton.setOnClickListener(this);
        sevenButton.setOnClickListener(this);
        eightButton.setOnClickListener(this);
        nineButton.setOnClickListener(this);
        multiplicationButton.setOnClickListener(this);
        fourButton.setOnClickListener(this);
        fiveButton.setOnClickListener(this);
        sixButton.setOnClickListener(this);
        subtractionButton.setOnClickListener(this);
        oneButton.setOnClickListener(this);
        twoButton.setOnClickListener(this);
        threeButton.setOnClickListener(this);
        additionButton.setOnClickListener(this);
        doubleZeroButton.setOnClickListener(this);
        zeroButton.setOnClickListener(this);
        decimalButton.setOnClickListener(this);
        equalsButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onPause() {
        // save instance variables
        displayString = displayTextView.getText().toString();
        Editor editor = prefs.edit();
        editor.putString("displayString", displayString);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get preferences
        theme = Integer.parseInt(prefs.getString("pref_theme", "0"));

        // get instance variables
        displayString = prefs.getString("displayString", "0");

        // set display
        displayTextView.setText(displayString);

        // changes theme of calculator ui
        changeTheme();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // attempt to get the fragment
        SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager()
                .findFragmentById(R.id.settings_fragment);

        // if the fragment is null, display the appropriate menu
        if (settingsFragment == null) {
            inflater.inflate(R.menu.activity_calculator, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case R.id.menu_past_calculations:
                startActivity(new Intent(getActivity(), PastCalculationsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        //String txt = "";
        StringBuilder sb = new StringBuilder();

        switch (v.getId()) {
            case R.id.acButton:
                reset();
                break;
            case R.id.plusMinusButton:
                handlePlusMinus();
                break;
            case R.id.percentButton:
                setValue(Double.toString((0.01 * Double.parseDouble(displayTextView.getText().toString()))));
                break;
            case R.id.divisionButton:
                handleEquals(4);
                break;
            case R.id.sevenButton:
                handleNumber(7);
                break;
            case R.id.eightButton:
                handleNumber(8);
                break;
            case R.id.nineButton:
                handleNumber(9);
                break;
            case R.id.multiplicationButton:
                handleEquals(3);
                break;
            case R.id.fourButton:
                handleNumber(4);
                break;
            case R.id.fiveButton:
                handleNumber(5);
                break;
            case R.id.sixButton:
                handleNumber(6);
                break;
            case R.id.subtractionButton:
                handleEquals(2);
                break;
            case R.id.oneButton:
                handleNumber(1);
                break;
            case R.id.twoButton:
                handleNumber(2);
                break;
            case R.id.threeButton:
                handleNumber(3);
                break;
            case R.id.additionButton:
                handleEquals(1);
                break;
            case R.id.deleteButton:
                handleDelete();
                break;
            case R.id.zeroButton:
                handleNumber(0);
                break;
            case R.id.decimalButton:
                handleDecimal();
                break;
            case R.id.equalsButton:
                handleEquals(0);
                break;
        }
        calculations = new Calculations();

        // get current display text and put into database
        calculations = new Calculations(calculations.getCurrentDate(), displayTextView.getText().toString());
        Log.i(TAG, "Date and time: " + calculations.getCurrentDate() + " Calculation: " + calculations.getCalculation());

        // adds calculation to database
        calculatorDB.addCalculation(calculations);
    }

    public void printDatabase() {
        List<Calculations> dbString = calculatorDB.databaseToString();
        String log ="";
        for (Calculations c : dbString) {
            log += "Id: " + c.get_id() + " Date: " + c.getCurrentDate()
                    + "\nCalculation: " + c.getCalculation() + "\n";
        }
        Log.i(TAG, log);
    }

    /******************
     * Helper methods *
     ******************/
    // changes theme of calculator display
    private void changeTheme() {
        if(theme == 0) {
            displayTextView.setTextColor(Color.BLACK);
            displayTextView.setBackgroundColor(Color.rgb(166, 211, 157));
            acButton.setTextColor(Color.WHITE);
            acButton.setBackgroundResource(R.drawable.ac_button_shape);
            plusMinusButton.setTextColor(Color.BLACK);
            plusMinusButton.setBackgroundResource(R.drawable.operation_button_shape);
            percentButton.setTextColor(Color.BLACK);
            percentButton.setBackgroundResource(R.drawable.operation_button_shape);
            divisionButton.setTextColor(Color.BLACK);
            divisionButton.setBackgroundResource(R.drawable.operation_button_shape);
            sevenButton.setTextColor(Color.WHITE);
            sevenButton.setBackgroundResource(R.drawable.digit_button_shape);
            eightButton.setTextColor(Color.WHITE);
            eightButton.setBackgroundResource(R.drawable.digit_button_shape);
            nineButton.setTextColor(Color.WHITE);
            nineButton.setBackgroundResource(R.drawable.digit_button_shape);
            multiplicationButton.setTextColor(Color.BLACK);
            multiplicationButton.setBackgroundResource(R.drawable.operation_button_shape);
            fourButton.setTextColor(Color.WHITE);
            fourButton.setBackgroundResource(R.drawable.digit_button_shape);
            fiveButton.setTextColor(Color.WHITE);
            fiveButton.setBackgroundResource(R.drawable.digit_button_shape);
            sixButton.setTextColor(Color.WHITE);
            sixButton.setBackgroundResource(R.drawable.digit_button_shape);
            subtractionButton.setTextColor(Color.BLACK);
            subtractionButton.setBackgroundResource(R.drawable.operation_button_shape);
            oneButton.setTextColor(Color.WHITE);
            oneButton.setBackgroundResource(R.drawable.digit_button_shape);
            twoButton.setTextColor(Color.WHITE);
            twoButton.setBackgroundResource(R.drawable.digit_button_shape);
            threeButton.setTextColor(Color.WHITE);
            threeButton.setBackgroundResource(R.drawable.digit_button_shape);
            additionButton.setTextColor(Color.BLACK);
            additionButton.setBackgroundResource(R.drawable.operation_button_shape);
            doubleZeroButton.setTextColor(Color.WHITE);
            doubleZeroButton.setBackgroundResource(R.drawable.digit_button_shape);
            zeroButton.setTextColor(Color.WHITE);
            zeroButton.setBackgroundResource(R.drawable.digit_button_shape);
            decimalButton.setTextColor(Color.WHITE);
            decimalButton.setBackgroundResource(R.drawable.digit_button_shape);
            equalsButton.setTextColor(Color.BLACK);
            equalsButton.setBackgroundResource(R.drawable.operation_button_shape);

        } else if(theme == 1) {
            displayTextView.setTextColor(Color.BLACK);
            displayTextView.setBackgroundColor(Color.rgb(166, 211, 157));
            acButton.setTextColor(Color.WHITE);
            acButton.setBackgroundResource(R.drawable.ac_button_shape_oldgloryblue);
            plusMinusButton.setTextColor(Color.WHITE);
            plusMinusButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            percentButton.setTextColor(Color.WHITE);
            percentButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            divisionButton.setTextColor(Color.WHITE);
            divisionButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            sevenButton.setTextColor(Color.RED);
            sevenButton.setBackgroundResource(R.drawable.even_row_button_white);
            eightButton.setTextColor(Color.RED);
            eightButton.setBackgroundResource(R.drawable.even_row_button_white);
            nineButton.setTextColor(Color.RED);
            nineButton.setBackgroundResource(R.drawable.even_row_button_white);
            multiplicationButton.setTextColor(Color.RED);
            multiplicationButton.setBackgroundResource(R.drawable.even_row_button_white);
            fourButton.setTextColor(Color.WHITE);
            fourButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            fiveButton.setTextColor(Color.WHITE);
            fiveButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            sixButton.setTextColor(Color.WHITE);
            sixButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            subtractionButton.setTextColor(Color.WHITE);
            subtractionButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            oneButton.setTextColor(Color.RED);
            oneButton.setBackgroundResource(R.drawable.even_row_button_white);
            twoButton.setTextColor(Color.RED);
            twoButton.setBackgroundResource(R.drawable.even_row_button_white);
            threeButton.setTextColor(Color.RED);
            threeButton.setBackgroundResource(R.drawable.even_row_button_white);
            additionButton.setTextColor(Color.RED);
            additionButton.setBackgroundResource(R.drawable.even_row_button_white);
            doubleZeroButton.setTextColor(Color.WHITE);
            doubleZeroButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            zeroButton.setTextColor(Color.WHITE);
            zeroButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            decimalButton.setTextColor(Color.WHITE);
            decimalButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
            equalsButton.setTextColor(Color.WHITE);
            equalsButton.setBackgroundResource(R.drawable.odd_row_button_oldgloryred);
        }
    }

    private void handleDelete() {
        String text = displayTextView.getText().toString();
        if(text.length() == 1) {
            displayTextView.setText("0");
        } else {
            displayTextView.setText(text.substring(0, text.length() - 1));
        }
    }

    // handles operators
    private void handleEquals(int newOperator) {
        if (hasChanged) {
            switch (operator) {
                case 1:
                    num = num + Double.parseDouble(displayTextView.getText().toString());
                    break;
                case 2:
                    num = num - Double.parseDouble(displayTextView.getText().toString());
                    break;
                case 3:
                    num = num * Double.parseDouble(displayTextView.getText().toString());
                    break;
                case 4:
                    num = num / Double.parseDouble(displayTextView.getText().toString());
                    break;
            }

            String text = Double.toString(num);
            displayTextView.setText(text);

            clearScreen = true;
            hasChanged = false;
        }
        operator = newOperator;
    }

    // handles digit buttons
    private void handleNumber(int num) {
        if (operator == 0)
            reset();

        String text = displayTextView.getText().toString();

        if (clearScreen) {
            text = "";
            clearScreen = false;
        } else if (text.equals("0"))
            text = "";

        text = text + Integer.toString(num);

        displayTextView.setText(text);
        hasChanged = true;
    }

    // sets value for percent button
    private void setValue(String value) {
        if (operator == 0)
            reset();

        if (clearScreen) {
            clearScreen = false;
        }
        displayTextView.setText(value);
    }

    // sets calculator display to zero
    private void reset() {
        num = 0;
        displayTextView.setText("0");
        operator = 1;
    }

    // handles decimal button
    private void handleDecimal() {
        if (operator == 0)
            reset();

        if (clearScreen) {
            displayTextView.setText("0.");
            clearScreen = false;
            hasChanged = true;
        } else {
            String text = displayTextView.getText().toString();

            if (!text.contains(".")) {
                displayTextView.append(".");
                hasChanged = true;
            }
        }
    }

    // handles plus minus button
    private void handlePlusMinus() {
        if (!clearScreen) {
            String text = displayTextView.getText().toString();
            if (!text.equals("0")) {
                if (text.charAt(0) == '-')
                    text = text.substring(1, text.length());
                else
                    text = "-" + text;

                    displayTextView.setText(text);
            }
        }
    }
}
