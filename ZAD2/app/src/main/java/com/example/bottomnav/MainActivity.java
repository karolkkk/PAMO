package com.example.bottomnav;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.text.TextWatcher;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private double height = 0.0;
    private double weight = 0.0;
    private double age = 0.0;
    private double miffinGender = 0.0;
    private double result_miffin = 0.0;
    private double mass2 = 0.0;
    private double height2 = 0.0;


    private TextView heightTextView;
    private TextView weightTextView;
    private TextView bmiTextView;
    private TextView categoryTextView;
    private TextView caloriesTextView;
    private ConstraintLayout bmi;
    private ConstraintLayout Mifflin;
    private ConstraintLayout welcome;
    private RadioButton rb1;
    private RadioButton rb2;
    private ImageView Img;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    welcome.setVisibility(View.VISIBLE);
                    bmi.setVisibility(View.INVISIBLE);
                    Mifflin.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_bmi:
                    mTextMessage.setText("BMI");
                    welcome.setVisibility(View.INVISIBLE);
                    bmi.setVisibility(View.VISIBLE);
                    Mifflin.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_miffin:
                    mTextMessage.setText("Miffin");
                    welcome.setVisibility(View.INVISIBLE);
                    bmi.setVisibility(View.INVISIBLE);
                    Mifflin.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        weightTextView= (TextView) findViewById(R.id.weightTextView);
        caloriesTextView = (TextView) findViewById(R.id.calories);

        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        categoryTextView = (TextView) findViewById(R.id.categoryTextView);
        welcome = (ConstraintLayout) findViewById(R.id.welcome);
        bmi = (ConstraintLayout) findViewById(R.id.bmi);
        Mifflin = (ConstraintLayout) findViewById(R.id.Mifflin);
        welcome.setVisibility(View.VISIBLE);
        bmi.setVisibility(View.INVISIBLE);
        Mifflin.setVisibility(View.INVISIBLE);

        EditText heightEditText = (EditText) findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);
        EditText weightEditText = (EditText) findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);
        EditText heightEditMiffin = (EditText) findViewById(R.id.heightEditMiffin);
        heightEditMiffin.addTextChangedListener(heightEditMiffinTextWatcher);
        EditText weightEditMiffin = (EditText) findViewById(R.id.weightEditMiffin);
        weightEditMiffin.addTextChangedListener(weightEditMiffinTextWatcher);
        EditText Age = (EditText) findViewById(R.id.Age);
        Age.addTextChangedListener(AgeTextWatcher);
        rb1 = (RadioButton) findViewById(R.id.radiofemale);
        rb2 = (RadioButton) findViewById(R.id.radioMale);
        Img = (ImageView) findViewById(R.id.imageView);
        Img.setVisibility(View.INVISIBLE);

    }
    private void calculate() {

        double m =height/100;

        double bmi = weight / (m * m);


        bmiTextView.setText(String.valueOf(bmi));

        if (bmi < 15) {
                categoryTextView.setText("extremely underweight");
            } else if (bmi >=15 && bmi <= 16) {
                categoryTextView.setText("severely underweight");
            } else if (bmi >16 && bmi <= 18.5) {
                categoryTextView.setText("underweight");
            } else if (bmi >18.5 && bmi <= 25) {
                categoryTextView.setText("healthy weight");
            } else if (bmi >25 && bmi <= 30) {
                categoryTextView.setText("overweight");
            } else if (bmi >30 && bmi <= 35) {
                categoryTextView.setText("moderately obese");
            } else if (bmi >35 && bmi <= 40) {
                categoryTextView.setText("severely obese");
            } else {
                categoryTextView.setText("extremely obese");
        }
    }
    private final TextWatcher heightEditTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                height = Double.parseDouble(s.toString());

            }
            catch (NumberFormatException e) {

                height = 0.0;
            }
            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };
    private final TextWatcher heightEditMiffinTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                height2 = Double.parseDouble(s.toString());

            }
            catch (NumberFormatException e) {

                height2 = 0.0;
            }
            calculateMiffin();
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };
    private final TextWatcher AgeTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                age = Double.parseDouble(s.toString());

            }
            catch (NumberFormatException e) {

                age = 0.0;
            }
            calculateMiffin();
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };



    private final TextWatcher weightEditTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                weight = Double.parseDouble(s.toString());

            }
            catch (NumberFormatException e) {

                weight = 0.0;
            }
            calculate();
        }


        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };
    private final TextWatcher weightEditMiffinTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                mass2 = Double.parseDouble(s.toString());

            }
            catch (NumberFormatException e) {

                mass2 = 0.0;
            }
            calculateMiffin();
        }


        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };

    public void onRadioButtonClicked(View v)
    {
        boolean  checked = ((RadioButton) v).isChecked();

        switch(v.getId()){

            case R.id.radiofemale:
                if(checked)
                    rb1.setTypeface(null, Typeface.BOLD_ITALIC);
                    rb2.setTypeface(null, Typeface.NORMAL);
                    rb2.setChecked(false);
                    calculateMiffin();
                break;

            case R.id.radioMale:
                if(checked)
                    rb2.setTypeface(null, Typeface.BOLD_ITALIC);
                    rb1.setTypeface(null, Typeface.NORMAL);
                    rb1.setChecked(false);
                    calculateMiffin();
                    break;
        }

    }
    private void calculateMiffin() {
        if(rb2.isChecked()) {
            miffinGender = 5;
        } else miffinGender = -161;
        result_miffin = (mass2 * 10) + ((height2 )* 6.25) - (age*5) + miffinGender;
        caloriesTextView.setText(String.valueOf(result_miffin));
        Img.setVisibility(View.VISIBLE);

        if (result_miffin < 1000) {
            Img.setImageResource(R.drawable.water);
        } else if (result_miffin >=1000 && result_miffin <= 1500) {
            Img.setImageResource(R.drawable.broccoli);
        } else if (result_miffin >1500 && result_miffin <= 2000) {
            Img.setImageResource(R.drawable.nooodles);
        } else if (result_miffin >2000 ) {
            Img.setImageResource(R.drawable.pizza);
        }
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
