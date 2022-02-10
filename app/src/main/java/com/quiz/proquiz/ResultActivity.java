package com.quiz.proquiz;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class ResultActivity extends AppCompatActivity {

    int POINTS = 10,correctAnswers,totalQuestions;
    int points;
    private FirebaseUser user;
    private FirebaseAuth mauth;
    private DatabaseReference dbRef;
    private FirebaseStorage storage;
    private FirebaseDatabase db;
    private String userId;

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

        mauth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        user =FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();

         correctAnswers = getIntent().getIntExtra("correct", 0);
         totalQuestions = getIntent().getIntExtra("total", 0);
         points = correctAnswers * POINTS;

        mysc.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        co.setText(String.valueOf(points));

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("coins")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        points = points+ Integer.parseInt(snapshot.getValue().toString());
                        snapshot.getRef().setValue(points);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}
