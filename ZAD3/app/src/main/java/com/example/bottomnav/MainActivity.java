package com.example.bottomnav;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private double height = 0.0;
    private double weight = 0.0;
    private double age = 0.0;
    private double miffinGender = 0.0;
    private double result_miffin = 0.0;
    private double mass2 = 0.0;
    private double height2 = 0.0;
    private int correctAnswers;
    private String correctAnswer;
    private int guessRows=2;
    private int totalGuesses;
    private SecureRandom random; // used to randomize the quiz
    private Handler handler;
    private List<String> fileNameList; // flag file names
    private List<String> quizCountriesList; // countries in current quiz
    private TextView questionNumberTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView bmiTextView;
    private TextView categoryTextView;
    private TextView caloriesTextView;
    private ConstraintLayout bmi;
    private ConstraintLayout Reset;
    private ConstraintLayout Mifflin;
    private ConstraintLayout welcome;
    private ConstraintLayout chart_layout;
    private ConstraintLayout Quiz;
    private RadioButton rb1;
    private RadioButton rb2;
    private Button resetBtn;
    private ImageView Img;
    private ImageView imageViewQuiz;
    private static final int FLAGS_IN_QUIZ = 5;
    private LinearLayout[] guessLinearLayouts;
    private TextView answerTextView;
    private static final String TAG = "MyActivity";



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
                    Quiz.setVisibility(View.INVISIBLE);
                    chart_layout.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_bmi:
                    mTextMessage.setText(R.string.bmi);
                    welcome.setVisibility(View.INVISIBLE);
                    bmi.setVisibility(View.VISIBLE);
                    Mifflin.setVisibility(View.INVISIBLE);
                    Quiz.setVisibility(View.INVISIBLE);
                    chart_layout.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_miffin:
                    mTextMessage.setText(R.string.Miffin);
                    welcome.setVisibility(View.INVISIBLE);
                    bmi.setVisibility(View.INVISIBLE);
                    Mifflin.setVisibility(View.VISIBLE);
                    Quiz.setVisibility(View.INVISIBLE);
                    chart_layout.setVisibility(View.INVISIBLE);
                    return true;
                    case R.id.navigation_Quiz:
                    mTextMessage.setText(R.string.Quiz);
                    welcome.setVisibility(View.INVISIBLE);
                    bmi.setVisibility(View.INVISIBLE);
                    Mifflin.setVisibility(View.INVISIBLE);
                    Quiz.setVisibility(View.VISIBLE);
                        chart_layout.setVisibility(View.INVISIBLE);
                        resetQuiz();
                    return true;
                case R.id.navigation_chart:
                    mTextMessage.setText(R.string.title_chart);
                    welcome.setVisibility(View.INVISIBLE);
                    bmi.setVisibility(View.INVISIBLE);
                    Mifflin.setVisibility(View.INVISIBLE);
                    Quiz.setVisibility(View.INVISIBLE);
                    chart_layout.setVisibility(View.VISIBLE);
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
        questionNumberTextView =  (TextView) findViewById(R.id.questionNumberTextView);
        chart_layout = (ConstraintLayout) findViewById(R.id.chart_layout);
        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        categoryTextView = (TextView) findViewById(R.id.categoryTextView);
        welcome = (ConstraintLayout) findViewById(R.id.welcome);
        bmi = (ConstraintLayout) findViewById(R.id.bmi);
        Mifflin = (ConstraintLayout) findViewById(R.id.Mifflin);
        Quiz = findViewById(R.id.Quiz);
        Reset= findViewById(R.id.Reset);
        Reset.setVisibility(View.INVISIBLE);
        Quiz.setVisibility(View.INVISIBLE);
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
        resetBtn= findViewById(R.id.resetBtn);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        Img = (ImageView) findViewById(R.id.imageView);
        Img.setVisibility(View.INVISIBLE);
        fileNameList = new ArrayList<>();
        quizCountriesList = new ArrayList<>();
        random = new SecureRandom();
        handler = new Handler();
        AssetManager assets = getAssets();
        questionNumberTextView.setText(
                getString(R.string.question, 1, FLAGS_IN_QUIZ));

// get references to GUI components
        imageViewQuiz = (ImageView) findViewById(R.id.imageViewQuiz);
        guessLinearLayouts = new LinearLayout[2];
        guessLinearLayouts[0] =
                (LinearLayout) findViewById(R.id.row1LinearLayout);
        guessLinearLayouts[1] =
                (LinearLayout) findViewById(R.id.row2LinearLayout);

        answerTextView = (TextView) findViewById(R.id.answerTextView);
        resetBtn.setOnClickListener(ResetListener);

        for (LinearLayout row : guessLinearLayouts) {
            for (int column = 0; column < row.getChildCount(); column++) {
                Button button = (Button) row.getChildAt(column);
                button.setOnClickListener(guessButtonListener);
            }
        }
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Example based on the
        //https://developers-dot-devsite-v2-prod.appspot.com/chart/interactive/docs/gallery/piechart.html
        String htmlData = "<html>"
                +"  <head>"
                +"    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>"
                +"    <script type=\"text/javascript\">"
                +"      google.charts.load('current', {'packages':['corechart']});"
                +"      google.charts.setOnLoadCallback(drawChart);"

                +"      function drawChart() {"

                +"        var data = google.visualization.arrayToDataTable(["
                +"          ['Age', 'Sleep(h)'],"
                +"          ['0-3 months)',  15],"
                +"          ['1-2yrs',  13],"
                +"          ['3-5yrs',  12],"
                +"          ['6-13', 10],"
                +"          ['14-17',  9]"
                +"        ]);"

                +"        var options = {"
                +"          title: 'Sleep needed by age chart',"
                +"          curveType: 'function',"
                +"          legend: { position: 'bottom' }"
                +"        };"

                +"        var chart = new google.visualization.LineChart(document.getElementById('chart'));"

                +"        chart.draw(data, options);"
                +"      }"
                +"    </script>"
                +"  </head>"
                +"  <body>"
                +"    <div id=\"chart\" style=\"width: 500px; height: 500px;\"></div>"
                +"  </body>"
                +"</html>";
        myWebView.loadData(htmlData, "text/html", "UTF-8");

    }

    public void resetQuiz() {
        // use AssetManager to get image file names for enabled regions
        AssetManager assets = getAssets();
        fileNameList.clear(); // empty list of image file names

        try {
            // loop through each region

            String[] paths = assets.list("X");

            for (String path : paths)
                fileNameList.add(path.replace(".png", ""));

        }
        catch (IOException exception) {
            Log.e(TAG, "Error loading image file names", exception);
        }

        correctAnswers = 0; // reset the number of correct answers made
        totalGuesses = 0; // reset the total number of guesses the user made
        quizCountriesList.clear(); // clear prior list of quiz countries

        int flagCounter = 1;
        int numberOfFlags = fileNameList.size();

        // add FLAGS_IN_QUIZ random file names to the quizCountriesList
        while (flagCounter <= FLAGS_IN_QUIZ) {
            int randomIndex = random.nextInt(numberOfFlags);

            // get the random file name
            String filename = fileNameList.get(randomIndex);

            // if the region is enabled and it hasn't already been chosen
            if (!quizCountriesList.contains(filename)) {
                quizCountriesList.add(filename); // add the file to the list
                ++flagCounter;
            }
        }

       loadNextFlag(); // start the quiz by loading the first flag
    }
    private void loadNextFlag() {
        // get file name of the next flag and remove it from the list
        //System.out.println(quizCountriesList);
        System.out.println(quizCountriesList.size());
        if(quizCountriesList.size()==0){
Quiz.setVisibility(View.INVISIBLE);
Reset.setVisibility(View.VISIBLE);

        }else {
            String nextImage = quizCountriesList.remove(0);
            correctAnswer = nextImage; // update the correct answer
            answerTextView.setText(""); // clear answerTextView

            // display current question number
            questionNumberTextView.setText(getString(
                    R.string.question, (correctAnswers + 1), FLAGS_IN_QUIZ));

            // extract the region from the next image's name
            String region = nextImage.substring(0, nextImage.indexOf('-'));

            // use AssetManager to load next image from assets folder
            AssetManager assets = this.getAssets();
            System.out.println(assets);

            // get an InputStream to the asset representing the next flag
            // and try to use the InputStream
            try (InputStream stream =
                         assets.open(region + "/" + nextImage + ".png")) {
                // load the asset as a Drawable and display on the flagImageView
                Drawable flag = Drawable.createFromStream(stream, nextImage);
                imageViewQuiz.setImageDrawable(flag);

                //animate(false); // animate the flag onto the screen
            } catch (IOException exception) {
                Log.e(TAG, "Error loading " + nextImage, exception);

            }

            Collections.shuffle(fileNameList); // shuffle file names

            // put the correct answer at the end of fileNameList
            int correct = fileNameList.indexOf(correctAnswer);
            fileNameList.add(fileNameList.remove(correct));
            System.out.println(fileNameList);
            // add 2, 4, 6 or 8 guess Buttons based on the value of guessRows
            for (int row = 0; row < guessRows; row++) {
                // place Buttons in currentTableRow
                for (int column = 0;
                     column < guessLinearLayouts[row].getChildCount();
                     column++) {
                    // get reference to Button to configure
                    Button newGuessButton =
                            (Button) guessLinearLayouts[row].getChildAt(column);
                    newGuessButton.setEnabled(true);

                    // get country name and set it as newGuessButton's text
                    String filename = fileNameList.get((row * 2) + column);
                    newGuessButton.setText(getFoodName(filename));
                }
            }

            // randomly replace one Button with the correct answer
            int row = random.nextInt(guessRows); // pick random row
            int column = random.nextInt(2); // pick random column
            LinearLayout randomRow = guessLinearLayouts[row]; // get the row
            String countryName = getFoodName(correctAnswer);
            ((Button) randomRow.getChildAt(column)).setText(countryName);
        }
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
    private OnClickListener ResetListener =new OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("asdfghjkl");
            Quiz.setVisibility(View.VISIBLE);
            Reset.setVisibility(View.INVISIBLE);
            resetQuiz();
        }};
    private OnClickListener guessButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Button guessButton = ((Button) v);
            String guess = guessButton.getText().toString();
            String answer = getFoodName(correctAnswer);
            ++totalGuesses; // increment number of guesses the user has made
            if (guess.equals(answer)) { // if the guess is correct
                ++correctAnswers; // increment the number of correct answers

                // display correct answer in green text
                answerTextView.setText(answer + "!");


                disableButtons(); // disable all guess Buttons
                loadNextFlag();

                if (correctAnswers == FLAGS_IN_QUIZ) {
                    disableButtons();
                    answerTextView.setText(getResources().getString(R.string.results));
                    //loadNextFlag();

            } else { // answer is correct but quiz is not over
               // loadNextFlag();
                answerTextView.setText(R.string.correct_answer);
            }
        }
            else { // answer was incorrect

                // display "Incorrect!" in red
                answerTextView.setText(R.string.incorrect_answer);
                guessButton.setEnabled(false); // disable incorrect answer
               // loadNextFlag();
            }
        }
    };

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
    }    private String getFoodName(String name) {
        return name.substring(name.indexOf('-') + 1).replace('_', ' ');
    }
    private void disableButtons() {
        for (int row = 0; row < guessRows; row++) {
            LinearLayout guessRow = guessLinearLayouts[row];
            for (int i = 0; i < guessRow.getChildCount(); i++)
                guessRow.getChildAt(i).setEnabled(false);
        }
    }
}
