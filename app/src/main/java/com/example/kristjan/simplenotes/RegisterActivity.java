package com.example.kristjan.simplenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserEmailField;
    private EditText mUserPasswordField;
    private EditText mConfirmPasswordField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get the required views
        mUserEmailField = findViewById(R.id.userEmail);
        mUserPasswordField = findViewById(R.id.userPassword);
        mConfirmPasswordField = findViewById(R.id.userConfirmPassword);

        // Set onClick listeners for buttons
        findViewById(R.id.registerButton).setOnClickListener(this);

        // Initialize authentication
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

    }
}
