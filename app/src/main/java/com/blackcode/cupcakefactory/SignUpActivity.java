package com.blackcode.cupcakefactory;

import static android.app.PendingIntent.getActivity;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class SignUpActivity extends AppCompatActivity {

    TextView LogText;
    EditText usernameSign,passwordSign,phoneNumber,email;
    FloatingActionButton signinButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        LogText = findViewById(R.id.LogText);
        usernameSign = findViewById(R.id.usernameSign);
        passwordSign = findViewById(R.id.passwordSign);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        signinButton = findViewById(R.id.signinButton);
        auth = FirebaseAuth.getInstance();


        LogText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String pass = passwordSign.getText().toString().trim();
                String userNm = usernameSign.getText().toString().trim();
                String pNumber = phoneNumber.getText().toString().trim();


                if(userEmail.isEmpty()){
                    email.setError("Email Cannot be Empty");
                }if(pass.isEmpty()){
                    passwordSign.setError("Password Cannot be Empty");
                }if(userNm.isEmpty()){
                    passwordSign.setError("Username Cannot be Empty");
                }
                if(pNumber.isEmpty()){
                    passwordSign.setError("Phone Number Cannot be Empty");
                }
                else{
                    auth.createUserWithEmailAndPassword(userEmail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userId = auth.getCurrentUser().getUid();
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

                                User user = new User(userNm, pNumber, userEmail,userId);
                                userRef.setValue(user);

                                Toast.makeText(SignUpActivity.this,"Register Successful. User ID: " + userId,Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();

                                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                finish();

                            } else {
                                Toast.makeText(SignUpActivity.this,"Registration Error"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }
}