package com.quiz.proquiz;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText emailText;
    Button resetButton;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        emailText = (EditText) findViewById(R.id.restText);
        resetButton = (Button) findViewById(R.id.resetButton);
        mauth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
        
    }
    public void resetPassword(){
        String email = emailText.getText().toString().trim();

        if(email.isEmpty()){
            emailText.setError("Email is Required");
            emailText.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Please provide valid email");
            emailText.requestFocus();
            return;
        }

        mauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your email to reset Password",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPassword.this,"Try again! Something wrong happened",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
