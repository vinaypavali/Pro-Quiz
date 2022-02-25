package com.quiz.proquiz.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;
import com.quiz.proquiz.R;
import com.quiz.proquiz.Users;


public class SignupActivity  extends AppCompatActivity {

    EditText rname;
    EditText rphone;
    EditText remail;
    EditText rpass;
    Button rsubmit;
    TextView rclick;

    private FirebaseAuth mauth;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        rname =(EditText)findViewById(R.id.nameBox);
        remail=(EditText)findViewById(R.id.emailBox);
        rphone=(EditText)findViewById(R.id.phoneBox);
        rpass =(EditText)findViewById(R.id.passwordBox);
        rsubmit =(Button)findViewById(R.id.submitBtn);
        rclick =(TextView)findViewById(R.id.clickLogin);
        progressBar=(ProgressBar) findViewById(R.id.pb);

        mauth= FirebaseAuth.getInstance();

        rsubmit.setOnClickListener(view -> {
            createUser();

        });
        rclick.setOnClickListener(view -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();

        });

    }
    private void createUser(){
        String name = rname.getText().toString();
        String phone = rphone.getText().toString();
        String email = remail.getText().toString();
        String password = rpass.getText().toString();


        if(TextUtils.isEmpty(name)){
            rname.setError("Name cannot be Empty");
            rname.requestFocus();

        }else if(TextUtils.isEmpty(phone)){
            remail.setError("Phone cannot be Empty");
            remail.requestFocus();

        }else if(TextUtils.isEmpty(email)){
            remail.setError("Email cannot be Empty");
            remail.requestFocus();

        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            remail.setError("Please provide valid email");
            remail.requestFocus();

        } else if(TextUtils.isEmpty(password)){
            rpass.setError("Password cannot be Empty");
            rpass.requestFocus();
        }else
        {
            progressBar.setVisibility(View.VISIBLE);

            mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Users users = new Users(name,email,password,phone);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(users);

                        Toast.makeText(SignupActivity.this,"User Registered successfully",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                        finish();
                    }else{
                        Toast.makeText(SignupActivity.this,"Registration Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            });
        }


    }
}
