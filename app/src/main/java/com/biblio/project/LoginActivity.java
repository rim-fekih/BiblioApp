package com.biblio.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailId, passw;
    ImageView btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public String mail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailId);
        passw = findViewById(R.id.passw);
        btnSignIn = findViewById(R.id.btnSignUp);
        tvSignUp = findViewById(R.id.sIn);



        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final String email = emailId.getText().toString();
                mail = email;
                String x = "rimfekih0@gmail.com";

                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    if ( mail.equals(x) ) {
                        Intent toAdmin = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(toAdmin);
                    }else{
                        Intent toUser = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(toUser);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();

                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                String pwd = passw.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    passw.setError("Please enter your password");
                    passw.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!email.isEmpty() && !pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mail = email;
                            String x = "rimfekih0@gmail.com";
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Error Please Login Again !", Toast.LENGTH_SHORT).show();
                            } else{
                                if ( mail.equals(x) ){
                                    Intent intToAdmin = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(intToAdmin);
                                }else{
                                    Intent intToHome = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(intToHome);
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intSignUp = new Intent ( LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}