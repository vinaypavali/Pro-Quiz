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
    int currentPos=0, correctAnswers = 0,timeValue =30,wrongCount=0;
    TextView qc,tc,mq,o1,o2,o3,o4;
    Button quit,next;
    String selectedOptionByUser="";
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

        o1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = o1.getText().toString();
                    o1.setBackgroundResource(R.drawable.option_red);
                    revelAnswer();
                    questionsArrayList.get(currentPos).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });
        o2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = o2.getText().toString();
                    o2.setBackgroundResource(R.drawable.option_red);
                    revelAnswer();
                    questionsArrayList.get(currentPos).setUserSelectedAnswer(selectedOptionByUser);
                }

            }
        });
        o3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = o3.getText().toString();
                    o3.setBackgroundResource(R.drawable.option_red);
                    revelAnswer();
                    questionsArrayList.get(currentPos).setUserSelectedAnswer(selectedOptionByUser);
                }

            }
        });
        o4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = o4.getText().toString();
                    o4.setBackgroundResource(R.drawable.option_red);
                    revelAnswer();
                    questionsArrayList.get(currentPos).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, MainActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                if(currentPos <= questionsArrayList.size()) {
                    currentPos++;
                    setNextQuestion();
                }
            }
        });

        getQuestions(questionsArrayList);
        resetTimer();
        setNextQuestion();

    }

    private void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tc.setText(String.valueOf(millisUntilFinished / 1000));

            }
            @Override
            public void onFinish() {
                startActivity(new Intent(QuizActivity.this, MainActivity.class));

            }
        }.start();

    }

    void revelAnswer(){
        final String getAnswer = questionsArrayList.get(currentPos).getAnswer();

        if(o1.getText().toString().equals(getAnswer)){
            o1.setBackgroundResource(R.drawable.option_green);
        }else  if(o2.getText().toString().equals(getAnswer)){
            o2.setBackgroundResource(R.drawable.option_green);
        }else  if(o3.getText().toString().equals(getAnswer)){
            o3.setBackgroundResource(R.drawable.option_green);
        }else  if(o4.getText().toString().equals(getAnswer)){
            o4.setBackgroundResource(R.drawable.option_green);
        }
    }

    void setNextQuestion() {
        if(timer != null)
            timer.cancel();
        resetTimer();
        timer.start();

        if(currentPos<questionsArrayList.size()){
            selectedOptionByUser="";
            reset();
            qc.setText(String.format("%d/%d",(currentPos+1),questionsArrayList.size()));
            mq.setText(questionsArrayList.get(currentPos).getQuestion());
            o1.setText(questionsArrayList.get(currentPos).getOption1());
            o2.setText(questionsArrayList.get(currentPos).getOption2());
            o3.setText(questionsArrayList.get(currentPos).getOption3());
            o4.setText(questionsArrayList.get(currentPos).getOption4());
        }else{
            gameWon();
        }

    }

    void reset(){
        o1.setBackgroundResource(R.drawable.border);
        o2.setBackgroundResource(R.drawable.border);
        o3.setBackgroundResource(R.drawable.border);
        o4.setBackgroundResource(R.drawable.border);
    }

    private void getQuestions(ArrayList<Questions> questionsArrayList) {

        questionsArrayList.add(new Questions("What is earth ?",
                "Planet","Sun","Human","Ball","Planet",""));
        questionsArrayList.add(new Questions("What is Sun ?",
                "Star","Planet","Human","ball","Star",""));

    }

    private void gameWon(){
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("correct", getCorrectAnswers());
        intent.putExtra("total", questionsArrayList.size());
        startActivity(intent);
        finish();

    }

    private int getCorrectAnswers(){
        for(int i=0;i<questionsArrayList.size();i++){
            final String getUserSelectedAnswer = questionsArrayList.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsArrayList.get(i).getAnswer();

            if(getUserSelectedAnswer.equals(getAnswer)){
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

}
