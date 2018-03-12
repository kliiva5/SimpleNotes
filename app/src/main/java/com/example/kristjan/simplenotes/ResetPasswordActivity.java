package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserPasswordResetEmail;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Get the required views
        mUserPasswordResetEmail = findViewById(R.id.mPasswordResetField);

        // Set onClickListeners to buttons
        findViewById(R.id.mPasswordResetButton).setOnClickListener(this);

        // Initialize authentication
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if the user is already signed in to prevent them from entering this activity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        redirectUser(currentUser);
    }

    /**
     *
     * @param email - describes the email that the user used to request a password reset
     *
     *  Method for sending emails to reset user passwords
     */
    private void sendUserPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            // The reset email was sent out successfully, redirect back to the
                            // LoginActivity and show a message to the user
                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            finish();
                            Toast.makeText(ResetPasswordActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            // The email was not sent out successfully - show the user a message
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.mPasswordResetButton) {
            sendUserPasswordResetEmail(mUserPasswordResetEmail.getText().toString());
        }
    }
}
