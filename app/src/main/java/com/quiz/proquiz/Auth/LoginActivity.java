package com.quiz.proquiz.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.quiz.proquiz.ForgotPassword;
import com.quiz.proquiz.MainActivity;
import com.quiz.proquiz.R;

public class LoginActivity extends AppCompatActivity {
    EditText lemail;
    EditText lpass;
    Button llogin;
    TextView lclick;
    TextView lhead;
    TextView lforgot;

    private FirebaseAuth mauth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        lemail=(EditText)findViewById(R.id.emailBox1);
        lpass =(EditText)findViewById(R.id.passwordBox1);
        llogin =(Button)findViewById(R.id.loginBtn);
        lclick =(TextView)findViewById(R.id.accountclick);
        lhead =(TextView)findViewById(R.id.tv1);
        lforgot =(TextView)findViewById(R.id.tv2);
        progressBar=(ProgressBar) findViewById(R.id.pb);

        mauth = FirebaseAuth.getInstance();



        llogin.setOnClickListener(view -> {
           loginUser();

        });

        lforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));

            }
        });

        lclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();

            }
        });
    }
    private void loginUser(){
        String email = lemail.getText().toString();
        String password = lpass.getText().toString();

        if(TextUtils.isEmpty(email)){
            lemail.setError("Email cannot be Empty");
            lemail.requestFocus();

        }else if(TextUtils.isEmpty(password)){
            lpass.setError("Password cannot be Empty");
            lpass.requestFocus();
        }else{
            progressBar.setVisibility(View.VISIBLE);

            mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(LoginActivity.this,"User Logged in successfully",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"Login Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }

                }
            });
        }
    }

}
