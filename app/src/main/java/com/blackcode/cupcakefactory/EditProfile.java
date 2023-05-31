package com.blackcode.cupcakefactory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity {

    ImageView backProfile;
    EditText edUn,edEmail,edFn,edPn,edAddress;
    TextView edDone;
    DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backProfile = findViewById(R.id.backProfile);
        edUn = findViewById(R.id.edUn);
        edEmail = findViewById(R.id.edEmail);
        edFn = findViewById(R.id.edFn);
        edPn = findViewById(R.id.edPn);
        edDone = findViewById(R.id.edDone);
        edAddress = findViewById(R.id.edAddress);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        boolean notNul =false;
        if (bundle != null){


            edUn.setText(bundle.getString("UserName"));
            edEmail.setText(bundle.getString("Email"));
            edFn.setText(bundle.getString("FullName"));
            edPn.setText(bundle.getString("Phone"));
            edAddress.setText(bundle.getString("Address"));

             notNul =true;
        }

        edDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserName =   edUn.getText().toString().trim();
                userRef.child("name").setValue(UserName);

                String FullName =   edFn.getText().toString().trim();
                userRef.child("fullName").setValue(FullName);

                String Email =   edEmail.getText().toString().trim();
                userRef.child("email").setValue(Email);

                String Phone =   edPn.getText().toString().trim();
                userRef.child("phoneNumber").setValue(Phone);

                String Address =   edAddress.getText().toString().trim();
                userRef.child("address").setValue(Address);

                Toast.makeText(EditProfile.this, "Data Updated", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });






    }



}