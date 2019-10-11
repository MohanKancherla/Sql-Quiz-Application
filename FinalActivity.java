package com.example.sqlquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import com.example.sqlquizapplication.QuizActivity.*;

import org.w3c.dom.Text;

/*

Final Screen which consists of test percentage, required pass percentage , test status and exit button
 */

public class FinalActivity extends AppCompatActivity {

   //public static final String EXTRA_SCORE="finalScore";

    private TextView finalTestScore;
    private TextView passTestScore;
    private TextView testStatus;

    private Button exitButton;

    int finalscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);

        finalTestScore = findViewById(R.id.textview_final_score);
        passTestScore= findViewById(R.id.textview_pass_score);
        testStatus = findViewById(R.id.textview_result);
        exitButton = findViewById(R.id.button_exit);

        Intent intent = getIntent();

        //Getting the values of quesion count and score from question screen activity
        String a1 = intent.getStringExtra("Value1");
        String a2 = intent.getStringExtra("Value2");

        //int n1 = Integer.parseInt(a1);
        Double n1 = Double.parseDouble(a1);
        Log.i("Info n1", String.valueOf(n1));
        //int n2 = Integer.parseInt(a2);
        Double n2= Double.parseDouble(a2);
        Log.i("Info n2", String.valueOf(n2));

        //for assignment of highscore
        int n3 = Integer.parseInt(a2);
        finalscore=n3;

        double score1 = n2/n1;
        int score = (int) (score1*100);
        Log.i("Info Score:" , String.valueOf(score));

        finalTestScore.setText("You have Scored: " + score +"%");

        passTestScore.setText("Pass Score: 60%");

        //Based on the exam score, it will display the status of exam
        if(score<60)
        {
            testStatus.setText("Final Result : FAIL");
        }
        else
        {
            testStatus.setText("Final Result : PASS");
        }

    }

    //on CLick event of exit button
    public void onExit(View n)
    {
        Intent exitIntent = new Intent(this,StartingScreenActivity.class);
//        exitIntent.putExtra(EXTRA_SCORE,finalscore);
//        setResult(RESULT_OK,exitIntent);
      startActivity(exitIntent);
//        QuizActivity a1= new QuizActivity();
//        a1.finishQuiz();

        //finishQuiz();
       //finish();
    }

//    public void finishQuiz(){
//
//        Intent resultIntent= new Intent();
//        resultIntent.putExtra(EXTRA_SCORE,finalscore);
//        setResult(RESULT_OK,resultIntent);
//
//        finish();
//    }
}
