package com.quiz.proquiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Questions> questions;
    int index=0;
    Questions question;
    CountDownTimer timer;
    FirebaseDatabase database;
    int correctAnswers = 0;
    TextView qc,tc,mq,o1,o2,o3,o4;
    



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();

        TextView qc = (TextView) findViewById(R.id.questionCounter);
        TextView tc = (TextView) findViewById(R.id.timer);

        TextView mq = (TextView) findViewById(R.id.question);

        TextView o1 = (TextView) findViewById(R.id.option_1);
        TextView o2 = (TextView) findViewById(R.id.option_2);
        TextView o3 = (TextView) findViewById(R.id.option_3);
        TextView o4 = (TextView) findViewById(R.id.option_4);

        Button quit = (Button) findViewById(R.id.quitBtn);
        Button next = (Button) findViewById(R.id.nextBtn);

        questions = new ArrayList<>();

        questions.add(new Questions("What is earth ?",
                "Planet","Sun","Human","Ball","Planet"));
        questions.add(new Questions("What is Sun ?",
                "Star","Planet","Human","ball","Star"));

    }
    public void setNextQuestion() {

        if(index < questions.size()){
            Questions question = questions.get(index);

        }
    }


}
