package com.quiz.proquiz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.quiz.proquiz.Auth.LoginActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    CountDownTimer timer;
    FirebaseDatabase database;
    int currentPos=0, correctCount = 0,timeValue =30,wrongCount=0;
    TextView qc,tc,mq,o1,o2,o3,o4;
    Button quit,next;
    ArrayList<Questions> questionsArrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();

         qc = (TextView) findViewById(R.id.questionCounter);
         tc = (TextView) findViewById(R.id.timer);

          mq = (TextView) findViewById(R.id.question);

          o1 = (TextView) findViewById(R.id.option_1);
          o2 = (TextView) findViewById(R.id.option_2);
          o3 = (TextView) findViewById(R.id.option_3);
          o4 = (TextView) findViewById(R.id.option_4);

         quit = (Button) findViewById(R.id.quitBtn);
         next = (Button) findViewById(R.id.nextBtn);

        questionsArrayList = new ArrayList<>();


        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, MainActivity.class));

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPos<questionsArrayList.size()) {
                    currentPos++;
                    setNextQuestion();
                }else{
                    gameWon();
                    Toast.makeText(QuizActivity.this,"Quiz Finished",Toast.LENGTH_SHORT).show();
                }
            }
        });


        getQuestions(questionsArrayList);
        resetTimer();
        setNextQuestion();

    }

    private void getQuestions(ArrayList<Questions> questionsArrayList) {

        questionsArrayList.add(new Questions("What is earth ?",
                "Planet","Sun","Human","Ball","Planet"));
        questionsArrayList.add(new Questions("What is Sun ?",
                "Star","Planet","Human","ball","Star"));

    }

    public void setNextQuestion() {
        if(timer != null)
            timer.cancel();
        resetTimer();
        timer.start();
        if(currentPos<questionsArrayList.size()){
            qc.setText(String.format("%d/%d",(currentPos+1),questionsArrayList.size()));
            mq.setText(questionsArrayList.get(currentPos).getQuestion());
            o1.setText(questionsArrayList.get(currentPos).getOption1());
            o2.setText(questionsArrayList.get(currentPos).getOption2());
            o3.setText(questionsArrayList.get(currentPos).getOption3());
            o4.setText(questionsArrayList.get(currentPos).getOption4());
        }

    }
    void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tc.setText(String.valueOf(millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                Dialog dialog=new Dialog(QuizActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.time_out);
                dialog.findViewById(R.id.tryBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(QuizActivity.this, MainActivity.class));
                    }
                });
                dialog.show();

            }
        }.start();

    }

    public void correct(){
        correctCount++;
        currentPos++;

    }
    public void wrong(){
        wrongCount++;
        currentPos++;

    }

    private void gameWon(){
        startActivity(new Intent(QuizActivity.this,ResultActivity.class));

    }

}
