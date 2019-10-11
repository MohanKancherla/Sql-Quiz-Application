package com.example.sqlquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


/*

Question activity screen :

Consists of question, options, confirm button, score, question count, difficulty level, countdown timer

 */

public class QuizActivity extends AppCompatActivity {

   public static final String EXTRA_SCORE="finalScore";

    private static final long COUNTDOWN_IN_MILLS= 30000;

    //for orientation
    private static final String KEY_SCORE="keyScore";
    private static final String KEY_QUESTION_COUNT="keyQuestionCount";
    private static final String KEY_MILLS_LEFT="keyMillisLeft";
    private static final String KEY_ANSWERED="keyAnswered";
    private static final String KEY_QUESTION_LIST="keyQuestionList";


    private TextView textViewQuestion;
    private TextView textviewScore;
    private TextView textViewQuestionCount;
    private TextView textviewDifficulty;
    private TextView textviewCountdown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;

    private ColorStateList textcolorDefaultRb;

    private ColorStateList textcolorDefaultcd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private ArrayList<Question> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean anSwered;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //QuizDbHelper qb= new QuizDbHelper(this);

        textViewQuestion = findViewById(R.id.text_view_question);
        textviewScore= findViewById(R.id.text_view_score);
        textViewQuestionCount= findViewById(R.id.text_view_question_count);
        textviewDifficulty= findViewById(R.id.text_view_difficulty);
        textviewCountdown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.radio_button1);
        rb2= findViewById(R.id.radio_button2);
        rb3= findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textcolorDefaultRb= rb1.getTextColors();
        textcolorDefaultcd = textviewCountdown.getTextColors();

        Intent intent = getIntent();

        String difficulty = intent.getStringExtra(StartingScreenActivity.EXTRA_DIFFICULTY);
        textviewDifficulty.setText("Difficulty: "+ difficulty);

        if(savedInstanceState == null) {
            QuizDbHelper dbHelper = new QuizDbHelper(this);
            //questionList = dbHelper.getAllQuestions();
            questionList=dbHelper.getQuestions(difficulty);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        }
        else
        {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
//            if( questionList== null){
//                finish();
//            }
            questionCountTotal= questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter-1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLS_LEFT);
            anSwered = savedInstanceState.getBoolean(KEY_ANSWERED);


            if(!anSwered)
            {
                startCountDown();
            }
            else
            {
                updateCountDownText();
                showSolution();
            }
        }

        // This is to check the select answer if option is selected or popup message if no option is selected

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!anSwered)
                {
                    if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked())
                    {
                        checkAnswer();
                    }
                    else
                    {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    showNextQuestion();
                }
            }
        });

    }

    //This function to call the next question
    private void showNextQuestion()
    {
        rb1.setTextColor(textcolorDefaultRb);
        rb2.setTextColor(textcolorDefaultRb);
        rb3.setTextColor(textcolorDefaultRb);

        rbGroup.clearCheck();

        if(questionCounter <questionCountTotal)
        {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            anSwered= false;
            buttonConfirmNext.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLS;
            startCountDown();
        }
        else
        {
//            FinalActivity a1= new FinalActivity();
//            a1.finishQuiz();
            //finish();
           finishQuiz();
            callFinalActivity();
        }

    }

    //This function to display the countdown timer
    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis =0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    //This is for time format and setting the timer to red color for last 10 seconds
    private void updateCountDownText(){
        int minutes = (int)(timeLeftInMillis/1000)/60;
        int seconds = (int)(timeLeftInMillis/1000)%60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        textviewCountdown.setText(timeFormatted);

        if(timeLeftInMillis<10000)
        {
            textviewCountdown.setTextColor(Color.RED);
        }
        else
        {
            textviewCountdown.setTextColor(textcolorDefaultcd);
        }

    }

    //This function to check the option selected and calculating score if option is correct
    private void checkAnswer(){
        anSwered= true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
                int answerNr = rbGroup.indexOfChild(rbSelected)+1;

        if(answerNr == currentQuestion.getAnswerNr())
        {
            score++;
            textviewScore.setText("Score: " + score);
        }

        showSolution();

    }

    //This function to display the correct answer in green color and setting the button to NEXT
    private void showSolution(){

        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch(currentQuestion.getAnswerNr())
        {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                break;

            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;

            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
        }

        if(questionCounter<questionCountTotal)
        {
            buttonConfirmNext.setText("NEXT");
        }
        else
        {
            buttonConfirmNext.setText("FINISH");
            //callFinalActivity();
        }
    }

    //This function to pass the score to starting screen activity
    private void finishQuiz(){

        Intent resultIntent= new Intent();
        resultIntent.putExtra(EXTRA_SCORE,score);
        setResult(RESULT_OK,resultIntent);

        finish();
    }

    // This function will display message press back again to exit, to make application user friendly
    @Override
    public void onBackPressed() {
        if(backPressedTime+2000 > System.currentTimeMillis()){
            finishQuiz();
//            FinalActivity a2= new FinalActivity();
//            a2.finishQuiz();
           // finish();
          //  finish();
        }
        else
        {
            Toast.makeText(this,"Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT,questionCounter);
        outState.putLong(KEY_MILLS_LEFT,timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED,anSwered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }

    //This function to call final activity screen with score, total questions
    private void callFinalActivity()
    {
        String totalnumQuestions;
        String finalScore;
        Intent finalIntent = new Intent(this,FinalActivity.class);


        totalnumQuestions = Integer.toString(questionCountTotal);
        Log.i("Info: totalnumQuestions",totalnumQuestions );
        finalScore = Integer.toString(score);
        Log.i("Info: finalScore",finalScore );

        finalIntent.putExtra("Value1",totalnumQuestions);
        finalIntent.putExtra("Value2",finalScore);
        startActivity(finalIntent);
    }
}
