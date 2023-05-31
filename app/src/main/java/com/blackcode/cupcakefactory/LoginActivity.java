package com.blackcode.cupcakefactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextView signupText,forgetPass;
    EditText username,password;
    FloatingActionButton loginButton,reset;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupText = findViewById(R.id.signupText);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        auth = FirebaseAuth.getInstance();
        forgetPass = findViewById(R.id.forgetPass);
        password.setVisibility(View.VISIBLE);
        reset = findViewById(R.id.reset);


        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (!user.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(user, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        password.setError("Empty fields are not allowed");
                    }
                } else if (user.isEmpty()) {
                    username.setError("Empty fields are not allowed");
                } else {
                    username.setError("Please enter correct email");
                }
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setVisibility(View.GONE);
                reset.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                forgetPass.setVisibility(View.GONE);

                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = username.getText().toString().trim();

                        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            auth.sendPasswordResetEmail(email)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(LoginActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                            password.setVisibility(View.VISIBLE);
                                            reset.setVisibility(View.GONE);
                                            loginButton.setVisibility(View.VISIBLE);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LoginActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else if (email.isEmpty()) {
                            username.setError("Empty fields are not allowed");
                        } else {
                            username.setError("Please enter correct email");
                        }
                    }
                });

            }
        });





    }
}