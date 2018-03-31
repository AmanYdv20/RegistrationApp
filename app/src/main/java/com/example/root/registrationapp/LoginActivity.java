package com.example.root.registrationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //start the activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }

        buttonLogin = (Button) findViewById(R.id.activity_main_confirm);
        editTextEmail = (EditText) findViewById(R.id.activity_main_email);
        editTextPassword = (EditText) findViewById(R.id.activity_main_password);
        textViewSignUp = (TextView) findViewById(R.id.activty_main_textView);
        progressDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonLogin){
            LoginUser();

        }

        if(view == textViewSignUp){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }
        
    }

    private void LoginUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter the Email first", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter the Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering Events");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //show new asctivty
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        } else {

                            Toast.makeText(LoginActivity.this,"Try Again Later", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}
