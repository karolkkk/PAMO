package com.pczapiewski.pjatk.mytipproject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends Activity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double height = 0.0;
    private double weight = 0.0;
    private double percent = 0.15;

    private TextView heightTextView;
    private TextView weightTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to programmatically manipulated TextViews
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        weightTextView= (TextView) findViewById(R.id.weightTextView);
       // percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        // set amountEditText's TextWatcher
        EditText heightEditText = (EditText) findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);
        EditText weightEditText = (EditText) findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);
    }

    // calculate and display tip and total amounts
    private void calculate() {
        // calculate the tip and total
        //double tip = height * percent;

        double tip = weight / (height * height);

        // display tip and total formatted as currency
        tipTextView.setText(String.valueOf(tip));

        if (tip < 15) {
            totalTextView.setText("very severely underweight");
        } else if (tip >=15 && tip <= 16) {
            totalTextView.setText("severely underweight");
        } else if (tip >16 && tip <= 18.5) {
            totalTextView.setText("underweight");
        } else if (tip >18.5 && tip <= 25) {
            totalTextView.setText("healthy weight");
        } else if (tip >25 && tip <= 30) {
            totalTextView.setText("overweight");
        } else if (tip >30 && tip <= 35) {
            totalTextView.setText("moderately obese");
        } else if (tip >35 && tip <= 40) {
            totalTextView.setText("severely obese");
        } else {
            totalTextView.setText("very severely obese");
        }
    }

    // listener object for the EditText's text-changed events
    private final TextWatcher heightEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try { // get bill amount and display currency formatted value
                height = Double.parseDouble(s.toString());
                heightTextView.setText(String.valueOf(height));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                heightTextView.setText("");
                height = 0.0;
            }
            calculate(); // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };
    private final TextWatcher weightEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try { // get bill amount and display currency formatted value
                weight = Double.parseDouble(s.toString());
                weightTextView.setText(String.valueOf(weight));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                weightTextView.setText("");
                weight = 0.0;
            }
            calculate(); // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };
}
