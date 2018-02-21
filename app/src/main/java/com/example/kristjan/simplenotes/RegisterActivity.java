package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
     public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        redirectUser(currentUser);
     }

     private void redirectUser(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            finish();
            startActivity(intent);
        }
     }

    /**
     *
     * @param email - describes the email that the user uses to sign up with
     * @param password - describes the password that the user uses to sign up with
     *
     * Method for signing up users with their email and password combination
     */
    private void registerUser(String email, String password){

        // Check if the user filled the required fields
        if(!checkFields()){
            // User did not fill the required fields
            return;
        }
        // User filled the required fields, continue with registering the user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Sign in was successful, redirect the user to the required activity
                            // TODO: Create dialog for redirecting
                            FirebaseUser newUser = mAuth.getCurrentUser();
                            redirectUser(newUser);
                        } else {
                            // Sign in failed, show an error to the user
                            Toast.makeText(RegisterActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     *
     * @return - describes the returned boolean value
     *
     * Method for checking if the user filled the required fields.
     */

    private boolean checkFields(){

        // TODO: Add password requirements (e.g at least 8 characters long, must contain numbers etc.)

        boolean valid = true;

        // Get the required field entries for validation
        String email = mUserEmailField.getText().toString();
        String password = mUserPasswordField.getText().toString();
        String confirmPassword = mConfirmPasswordField.getText().toString();

        // Check the corresponding fields to see if the user entered the required data
        if(TextUtils.isEmpty(email)){
            mUserEmailField.setError("Please enter an email address");
            valid = false;
        } else {
            mUserEmailField.setError(null);
        }

        if(TextUtils.isEmpty(password)){
            mUserPasswordField.setError("Please enter a password");
            valid = false;
        } else {
            mUserPasswordField.setError(null);
        }

        if(TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordField.setError("Please enter your password again");
            valid = false;
        } else {
            mConfirmPasswordField.setError(null);
        }

        if(!password.equals(confirmPassword)){
            mConfirmPasswordField.setError("Passwords must match");
            valid = false;
        } else {
            mConfirmPasswordField.setError(null);
        }

        return valid;

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.registerButton){
            registerUser(mUserEmailField.getText().toString(), mUserPasswordField.getText().toString());
        }
    }
}
