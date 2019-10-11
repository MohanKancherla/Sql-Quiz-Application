package com.example.sqlquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/*

CSCI 5211 Final Project : SQL QUIZ application
Written by Mohan Kancherla

This project consists of three activity screens

1. Starting Screen:

    Start Quiz button   : This will help in move to the next page when user selects respective level.
    HighScore Textview  : It will show the high scored achieved by the user in all the attempts
    Levels Spinner      : It will consists of three levels Easy, Medium and Hard. Based on the level selection questions will be changed

2. Question Screen Activity :

    This Screen consists of Question on the center of page with three options provided and confirm button which is for finalising your answer. Once user clicks confirm,
    it will check the solution and display which answer is correct for particular question (Answer will be displayed in green color). It has Next button which will nagivate
    to the next question.

    On the Top left corner, there is score, question count and difficulty level

    On the Top right corner, there is count down timer of 30 seconds for each questions

3. Final activity screen:

    This Screen shows the total percentage of marks achieved in the exam, required pass percentage and exam status (FAIL or PASS).

    It has Exit button which will nagivate to the starting screen again if user want to attempt again.

Future Implementations of project:

1. Creating User database to register for the exam and analysing his/her test scores
2. Providing enough materials and tutorial links to excel in the skill
3. This application is currently limited to one Skill, I will make changes to add the tests for all skills and materials as well
4. Addition of animations and graphics to the application

 */

public class StartingScreenActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ=1;
    public static final String EXTRA_DIFFICULTY ="extraDifficulty";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE ="keyHighscore";

    private TextView textViewHighscore;

    private Spinner spinnerDifficulty;

    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        textViewHighscore = findViewById(R.id.text_high_score);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        String[] difficultyLevels = Question.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,difficultyLevels);
        adapterDifficulty.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);

        loadHighscore();

        Button buttonStartQuiz= findViewById(R.id.button_start_quiz);
//        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                quizClick(View n );
//            }
//        });
    }

    //start button onClick Functionality which will call to next screen
    public void quizClick(View n){

        String difficulty = spinnerDifficulty.getSelectedItem().toString();
        Intent addQuizActivityintent = new Intent(this,QuizActivity.class);
       // startActivity(addQuizActivityintent);
        addQuizActivityintent.putExtra(EXTRA_DIFFICULTY,difficulty);
        startActivityForResult(addQuizActivityintent,REQUEST_CODE_QUIZ);

//        Intent addexitIntent = new Intent(this,FinalActivity.class);
//        startActivityForResult(addexitIntent,REQUEST_CODE_QUIZ);
    }


    //This function for Checking the highscore in an attempt
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_CODE_QUIZ)
        {
            if(resultCode == RESULT_OK)
            {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE,0);
                //int score = data.getIntExtra(FinalActivity.EXTRA_SCORE,0);
                Log.i("Info: score is", String.valueOf(score));
                Log.i("Inf0: highscore is", String.valueOf(highScore));
                if(score>highScore){
                    updateHighscore(score);
                }
            }
        }
    }

    //This function will set the highscore textview
    private void loadHighscore(){

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGHSCORE,0);
        textViewHighscore.setText("HighScore: "+ highScore);

    }

    //If present score is greater than last attempt, this function will be called to update high score
    private void updateHighscore(int highScoreNew)
    {
        highScore= highScoreNew;

        textViewHighscore.setText("HighScore: "+ highScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(KEY_HIGHSCORE,highScore);
        editor.apply();
    }
}
