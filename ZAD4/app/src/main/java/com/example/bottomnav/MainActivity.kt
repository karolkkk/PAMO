package com.example.bottomnav

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.text.TextWatcher
import android.view.View.OnClickListener
import java.io.IOException
import java.security.SecureRandom
import java.util.ArrayList
import java.util.Collections


class MainActivity : AppCompatActivity() {
    private var mTextMessage: TextView? = null
    private var height = 0.0
    private var weight = 0.0
    private var age = 0.0
    private var miffinGender = 0.0
    private var result_miffin = 0.0
    private var mass2 = 0.0
    private var height2 = 0.0
    private var correctAnswers: Int = 0
    private var correctAnswer: String = ""
    private val guessRows = 2
    private var totalGuesses: Int = 0
    private var random: SecureRandom? = null // used to randomize the quiz
    private var handler: Handler? = null
    private var fileNameList: MutableList<String>? = null // flag file names
    private var quizCountriesList: MutableList<String>? = null // countries in current quiz
    private var questionNumberTextView: TextView? = null
    private var heightTextView: TextView? = null
    private var weightTextView: TextView? = null
    private var bmiTextView: TextView? = null
    private var categoryTextView: TextView? = null
    private var caloriesTextView: TextView? = null
    private var bmi: ConstraintLayout? = null
    private var Reset: ConstraintLayout? = null
    private var Mifflin: ConstraintLayout? = null
    private var welcome: ConstraintLayout? = null
    private var chart_layout: ConstraintLayout? = null
    private var Quiz: ConstraintLayout? = null
    private var rb1: RadioButton? = null
    private var rb2: RadioButton? = null
    private var resetBtn: Button? = null
    private var Img: ImageView? = null
    private var imageViewQuiz: ImageView? = null
    private var guessLinearLayouts: Array<LinearLayout?>? = null
    private var answerTextView: TextView? = null


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                welcome!!.visibility = View.VISIBLE
                bmi!!.visibility = View.INVISIBLE
                Mifflin!!.visibility = View.INVISIBLE
                Quiz!!.visibility = View.INVISIBLE
                chart_layout!!.visibility = View.INVISIBLE
                Reset!!.visibility = View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bmi -> {
                mTextMessage!!.setText(R.string.bmi)
                welcome!!.visibility = View.INVISIBLE
                bmi!!.visibility = View.VISIBLE
                Mifflin!!.visibility = View.INVISIBLE
                Quiz!!.visibility = View.INVISIBLE
                chart_layout!!.visibility = View.INVISIBLE
                Reset!!.visibility = View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_miffin -> {
                mTextMessage!!.setText(R.string.Miffin)
                welcome!!.visibility = View.INVISIBLE
                bmi!!.visibility = View.INVISIBLE
                Mifflin!!.visibility = View.VISIBLE
                Quiz!!.visibility = View.INVISIBLE
                chart_layout!!.visibility = View.INVISIBLE
                Reset!!.visibility = View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Quiz -> {
                mTextMessage!!.setText(R.string.Quiz)
                welcome!!.visibility = View.INVISIBLE
                bmi!!.visibility = View.INVISIBLE
                Mifflin!!.visibility = View.INVISIBLE
                Quiz!!.visibility = View.VISIBLE
                chart_layout!!.visibility = View.INVISIBLE
                resetQuiz()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chart -> {
                mTextMessage!!.setText(R.string.title_chart)
                welcome!!.visibility = View.INVISIBLE
                bmi!!.visibility = View.INVISIBLE
                Mifflin!!.visibility = View.INVISIBLE
                Quiz!!.visibility = View.INVISIBLE
                chart_layout!!.visibility = View.VISIBLE
                Reset!!.visibility = View.INVISIBLE
            }
        }
        false
    }
    private val heightEditTextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            try {
                height = java.lang.Double.parseDouble(s.toString())

            } catch (e: NumberFormatException) {

                height = 0.0
            }

            calculate()
        }

        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }
    private val heightEditMiffinTextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            try {
                height2 = java.lang.Double.parseDouble(s.toString())

            } catch (e: NumberFormatException) {

                height2 = 0.0
            }

            calculateMiffin()
        }

        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }
    private val AgeTextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            try {
                age = java.lang.Double.parseDouble(s.toString())

            } catch (e: NumberFormatException) {

                age = 0.0
            }

            calculateMiffin()
        }

        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }


    private val weightEditTextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            try {
                weight = java.lang.Double.parseDouble(s.toString())

            } catch (e: NumberFormatException) {

                weight = 0.0
            }

            calculate()
        }


        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }
    private val weightEditMiffinTextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            try {
                mass2 = java.lang.Double.parseDouble(s.toString())

            } catch (e: NumberFormatException) {

                mass2 = 0.0
            }

            calculateMiffin()
        }


        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }
    private val ResetListener = OnClickListener {
        Quiz!!.visibility = View.VISIBLE
        Reset!!.visibility = View.INVISIBLE
        resetQuiz()
    }
    private val guessButtonListener = OnClickListener { v ->
        val guessButton = v as Button
        val guess = guessButton.text.toString()
        val answer = getFoodName(correctAnswer)
        ++totalGuesses // increment number of guesses the user has made
        if (guess.compareTo(answer,true) == 0) { // if the guess is correct
            ++correctAnswers // increment the number of correct answers

            // display correct answer in green text
            answerTextView!!.text = "$answer!"


            disableButtons() // disable all guess Buttons
            loadNextFlag()

            if (correctAnswers == FLAGS_IN_QUIZ) {
                disableButtons()
                answerTextView!!.text = resources.getString(R.string.results)
                //loadNextFlag();

            } else { // answer is correct but quiz is not over
                // loadNextFlag();
                answerTextView!!.setText(R.string.correct_answer)
            }
        } else { // answer was incorrect

            // display "Incorrect!" in red
            answerTextView!!.setText(R.string.incorrect_answer)
            guessButton.isEnabled = false // disable incorrect answer
            // loadNextFlag();
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        mTextMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        heightTextView = findViewById<View>(R.id.heightTextView) as TextView
        weightTextView = findViewById<View>(R.id.weightTextView) as TextView
        caloriesTextView = findViewById<View>(R.id.calories) as TextView
        questionNumberTextView = findViewById<View>(R.id.questionNumberTextView) as TextView
        chart_layout = findViewById<View>(R.id.chart_layout) as ConstraintLayout
        bmiTextView = findViewById<View>(R.id.bmiTextView) as TextView
        categoryTextView = findViewById<View>(R.id.categoryTextView) as TextView
        welcome = findViewById<View>(R.id.welcome) as ConstraintLayout
        bmi = findViewById<View>(R.id.bmi) as ConstraintLayout
        Mifflin = findViewById<View>(R.id.Mifflin) as ConstraintLayout
        Quiz = findViewById(R.id.Quiz)
        Reset = findViewById(R.id.Reset)
        Reset!!.visibility = View.INVISIBLE
        Quiz!!.visibility = View.INVISIBLE
        welcome!!.visibility = View.VISIBLE
        bmi!!.visibility = View.INVISIBLE
        Mifflin!!.visibility = View.INVISIBLE

        val heightEditText = findViewById<View>(R.id.heightEditText) as EditText
        heightEditText.addTextChangedListener(heightEditTextWatcher)
        val weightEditText = findViewById<View>(R.id.weightEditText) as EditText
        weightEditText.addTextChangedListener(weightEditTextWatcher)
        val heightEditMiffin = findViewById<View>(R.id.heightEditMiffin) as EditText
        heightEditMiffin.addTextChangedListener(heightEditMiffinTextWatcher)
        val weightEditMiffin = findViewById<View>(R.id.weightEditMiffin) as EditText
        weightEditMiffin.addTextChangedListener(weightEditMiffinTextWatcher)
        val Age = findViewById<View>(R.id.Age) as EditText
        Age.addTextChangedListener(AgeTextWatcher)
        rb1 = findViewById<View>(R.id.radiofemale) as RadioButton
        rb2 = findViewById<View>(R.id.radioMale) as RadioButton
        resetBtn = findViewById(R.id.resetBtn)
        val myWebView = findViewById<View>(R.id.webview) as WebView
        Img = findViewById<View>(R.id.imageView) as ImageView
        Img!!.visibility = View.INVISIBLE
        fileNameList = ArrayList()
        quizCountriesList = ArrayList()
        random = SecureRandom()
        handler = Handler()
        val assets = assets
        questionNumberTextView!!.text = getString(R.string.question, 1, FLAGS_IN_QUIZ)

        // get references to GUI components
        imageViewQuiz = findViewById<View>(R.id.imageViewQuiz) as ImageView
        guessLinearLayouts = arrayOfNulls(2)
        guessLinearLayouts!![0] = findViewById<View>(R.id.row1LinearLayout) as LinearLayout
        guessLinearLayouts!![1] = findViewById<View>(R.id.row2LinearLayout) as LinearLayout

        answerTextView = findViewById<View>(R.id.answerTextView) as TextView
        resetBtn!!.setOnClickListener(ResetListener)

        for (row in guessLinearLayouts!!) {
            if (row != null) {
                for (column in 0 until row.childCount) {
                    val button = row.getChildAt(column) as Button
                    button.setOnClickListener(guessButtonListener)
                }
            }
        }
        val webSettings = myWebView.settings
        webSettings.javaScriptEnabled = true

        //Example based on the
        //https://developers-dot-devsite-v2-prod.appspot.com/chart/interactive/docs/gallery/piechart.html
        val htmlData = ("<html>"
                + "  <head>"
                + "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>"
                + "    <script type=\"text/javascript\">"
                + "      google.charts.load('current', {'packages':['corechart']});"
                + "      google.charts.setOnLoadCallback(drawChart);"

                + "      function drawChart() {"

                + "        var data = google.visualization.arrayToDataTable(["
                + "          ['Age', 'Sleep(h)'],"
                + "          ['0-3 months)',  15],"
                + "          ['1-2yrs',  13],"
                + "          ['3-5yrs',  12],"
                + "          ['6-13', 10],"
                + "          ['14-17',  9]"
                + "        ]);"

                + "        var options = {"
                + "          title: 'Sleep needed by age chart',"
                + "          curveType: 'function',"
                + "          legend: { position: 'bottom' }"
                + "        };"

                + "        var chart = new google.visualization.LineChart(document.getElementById('chart'));"

                + "        chart.draw(data, options);"
                + "      }"
                + "    </script>"
                + "  </head>"
                + "  <body>"
                + "    <div id=\"chart\" style=\"width: 500px; height: 500px;\"></div>"
                + "  </body>"
                + "</html>")
        myWebView.loadData(htmlData, "text/html", "UTF-8")

    }


    fun resetQuiz() {
        // use AssetManager to get image file names for enabled regions
        val assets = assets
        fileNameList!!.clear() // empty list of image file names

        try {
            // loop through each region

            val paths = assets.list("X")

            for (path in paths!!)
                fileNameList!!.add(path.replace(".png", "", true))

        } catch (exception: IOException) {
            Log.e(TAG, "Error loading image file names", exception)
        }

        correctAnswers = 0 // reset the number of correct answers made
        totalGuesses = 0 // reset the total number of guesses the user made
        quizCountriesList!!.clear() // clear prior list of quiz countries

        var flagCounter = 1
        val numberOfFlags = fileNameList!!.size

        // add FLAGS_IN_QUIZ random file names to the quizCountriesList
        while (flagCounter <= FLAGS_IN_QUIZ) {
            val randomIndex = random!!.nextInt(numberOfFlags)

            // get the random file name
            val filename = fileNameList!![randomIndex]

            // if the region is enabled and it hasn't already been chosen
            if (!quizCountriesList!!.contains(filename)) {
                quizCountriesList!!.add(filename) // add the file to the list
                ++flagCounter
            }
        }

        loadNextFlag() // start the quiz by loading the first flag
    }

    private fun loadNextFlag() {
        // get file name of the next flag and remove it from the list
        //System.out.println(quizCountriesList);
        println(quizCountriesList!!.size)
        if (quizCountriesList!!.size == 0) {
            Quiz!!.visibility = View.INVISIBLE
            Reset!!.visibility = View.VISIBLE

        } else {
            val nextImage = quizCountriesList!!.removeAt(0)
            correctAnswer = nextImage // update the correct answer
            answerTextView!!.text = "" // clear answerTextView

            // display current question number
            questionNumberTextView!!.text = getString(
                    R.string.question, correctAnswers + 1, FLAGS_IN_QUIZ)

            // extract the region from the next image's name
            val region = nextImage.substring(0, nextImage.indexOf('-'))

            // use AssetManager to load next image from assets folder
            val assets = this.assets


            // get an InputStream to the asset representing the next flag
            // and try to use the InputStream
            try {
                assets.open(region + "/" + nextImage + ".png").use { stream ->
                    // load the asset as a Drawable and display on the flagImageView
                    val flag = Drawable.createFromStream(stream, nextImage)
                    imageViewQuiz!!.setImageDrawable(flag)

                    //animate(false); // animate the flag onto the screen
                }
            } catch (exception: IOException) {
                Log.e(TAG, "Error loading $nextImage", exception)

            }

            Collections.shuffle(fileNameList) // shuffle file names

            // put the correct answer at the end of fileNameList
            val correct = fileNameList!!.indexOf(correctAnswer)
            fileNameList!!.add(fileNameList!!.removeAt(correct))
            println(fileNameList)
            // add 2, 4, 6 or 8 guess Buttons based on the value of guessRows
            for (row in 0 until guessRows) {
                // place Buttons in currentTableRow
                for (column in 0 until guessLinearLayouts!![row]!!.childCount) {
                    // get reference to Button to configure
                    val newGuessButton = guessLinearLayouts!![row]!!.getChildAt(column) as Button
                    newGuessButton.isEnabled = true

                    // get country name and set it as newGuessButton's text
                    val filename = fileNameList!![row * 2 + column]
                    newGuessButton.text = getFoodName(filename)
                }
            }

            // randomly replace one Button with the correct answer
            val row = random!!.nextInt(guessRows) // pick random row
            val column = random!!.nextInt(2) // pick random column
            val randomRow = guessLinearLayouts!![row] // get the row
            val countryName = getFoodName(correctAnswer)
            if (randomRow != null) {
                (randomRow.getChildAt(column) as Button).text = countryName
            }
        }
    }

    private fun calculate() {

        val m = height / 100

        val bmi = weight / (m * m)


        bmiTextView!!.text = bmi.toString()

        if (bmi < 15) {
            categoryTextView!!.text = "extremely underweight"
        } else if (bmi >= 15 && bmi <= 16) {
            categoryTextView!!.text = "severely underweight"
        } else if (bmi > 16 && bmi <= 18.5) {
            categoryTextView!!.text = "underweight"
        } else if (bmi > 18.5 && bmi <= 25) {
            categoryTextView!!.text = "healthy weight"
        } else if (bmi > 25 && bmi <= 30) {
            categoryTextView!!.text = "overweight"
        } else if (bmi > 30 && bmi <= 35) {
            categoryTextView!!.text = "moderately obese"
        } else if (bmi > 35 && bmi <= 40) {
            categoryTextView!!.text = "severely obese"
        } else {
            categoryTextView!!.text = "extremely obese"
        }
    }

    fun onRadioButtonClicked(v: View) {
        val checked = (v as RadioButton).isChecked

        when (v.getId()) {

            R.id.radiofemale -> {
                if (checked)
                    rb1!!.setTypeface(null, Typeface.BOLD_ITALIC)
                rb2!!.setTypeface(null, Typeface.NORMAL)
                rb2!!.isChecked = false
                calculateMiffin()
            }

            R.id.radioMale -> {
                if (checked)
                    rb2!!.setTypeface(null, Typeface.BOLD_ITALIC)
                rb1!!.setTypeface(null, Typeface.NORMAL)
                rb1!!.isChecked = false
                calculateMiffin()
            }
        }

    }

    private fun calculateMiffin() {
        if (rb2!!.isChecked) {
            miffinGender = 5.0
        } else
            miffinGender = -161.0
        result_miffin = mass2 * 10 + height2 * 6.25 - age * 5 + miffinGender
        caloriesTextView!!.text = result_miffin.toString()
        Img!!.visibility = View.VISIBLE

        if (result_miffin < 1000) {
            Img!!.setImageResource(R.drawable.water)
        } else if (result_miffin >= 1000 && result_miffin <= 1500) {
            Img!!.setImageResource(R.drawable.broccoli)
        } else if (result_miffin > 1500 && result_miffin <= 2000) {
            Img!!.setImageResource(R.drawable.nooodles)
        } else if (result_miffin > 2000) {
            Img!!.setImageResource(R.drawable.pizza)
        }
    }

    private fun getFoodName(name: String): String {
        return name.substring(name.indexOf('-') + 1).replace('_', ' ')
    }

    private fun disableButtons() {
        for (row in 0 until guessRows) {
            val guessRow = guessLinearLayouts!![row]
            for (i in 0 until guessRow!!.childCount)
                guessRow.getChildAt(i).isEnabled = false
        }
    }

    companion object {
        private val FLAGS_IN_QUIZ = 5
        private val TAG = "MyActivity"
    }
}
