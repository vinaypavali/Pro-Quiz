package com.quiz.proquiz;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        ImageView imv = (ImageView) findViewById(R.id.winner);
        TextView cn = (TextView) findViewById(R.id.cong);
        TextView mysc = (TextView) findViewById(R.id.myscore);
        TextView tv7 = (TextView) findViewById(R.id.textView7);
        TextView co = (TextView) findViewById(R.id.mycoins);


    }
}
