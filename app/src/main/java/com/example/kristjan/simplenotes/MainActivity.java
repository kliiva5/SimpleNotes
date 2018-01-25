package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize authentication
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if the user is already singed in update the UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * @param user - describes the currently signed in user
     *
     *  Method for updating the UI according to sign-in status
     */
    private void updateUI(FirebaseUser user) {
        if(user != null) {
            // TODO: Get the current user's profile data and set it to the top bar
        } else {
            // The user was not signed in show the default homepage (MainActivity in this case)
            // TODO: Hide the required elements when the user is not signed in
        }
    }

    /* Called when the user taps the Begin button */
    public void beginButton(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        // Finish the current activity to prevent back button loops
        finish();
        startActivity(intent);
    }

}
