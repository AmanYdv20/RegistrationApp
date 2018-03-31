package com.example.root.registrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userEmail;
    private Button logoutButton;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextEvent;
    private Button eventButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextName= findViewById(R.id.activity_profile_name);
        editTextEvent = findViewById(R.id.activity_event);
        eventButton = findViewById(R.id.event_button);

        userEmail = (TextView) findViewById(R.id.activity_main_textprofile);
        logoutButton = (Button) findViewById(R.id.activity_main_logout);
        userEmail.setText("Welcome" +user.getEmail());

        logoutButton.setOnClickListener(this);
        eventButton.setOnClickListener(this);
    }

    private void saveUserInformation(){
        String name = editTextName.getText().toString().trim();
        String event = editTextEvent.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, event);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "event is registering", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {

        if(view == logoutButton){

            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == eventButton){
            saveUserInformation();
        }

    }
}
