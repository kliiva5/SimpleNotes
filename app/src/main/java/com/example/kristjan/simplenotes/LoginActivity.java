package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserEmailField;
    private EditText mUserPasswordField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get the required views
        mUserEmailField = findViewById(R.id.emailField);
        mUserPasswordField = findViewById(R.id.passwordField);

        // Get the required buttons and add an event listener for them (in this case an OnClickListener)
        findViewById(R.id.loginButton).setOnClickListener(this);

        // Initialize authentication
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if the user is already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        redirectUser(currentUser);
    }

    /**
     *
     * @param user - describes the currently logged in user
     *
     *  Method for redirecting users if required
     *  Users will only be redirected if they are not logged in
     */
    private void redirectUser(FirebaseUser user) {
        if (user != null) {
            // Redirect the user to the Profile Activity
            Intent intent = new Intent(this, ProfileActivity.class);
            // Finish the current activity to prevent loops
            finish();
            startActivity(intent);
        } else {
            // The user was not signed in yet - remain on the same activity
            return;
        }
    }

    /**
     *
     * @param email - describes the email that user uses to log in with
     * @param password - describes the password that the user uses to log in with
     *
     *  Method for logging in users with their email and password combination
     */
    private void logIn(String email, String password) {

        // TODO: Show a progress dialog to hide authentication delay

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Sign in was successful - redirect the user to the Profile Activity
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    redirectUser(currentUser);
                } else {
                    // Sign in failed - show and error to the user
                    Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.loginButton) {
            logIn(mUserEmailField.getText().toString(), mUserPasswordField.getText().toString());
        }
    }
}
