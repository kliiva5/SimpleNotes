package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        redirectUser(currentUser);
    }

    /**
     *
     * @param user - describes the Firebase user object
     *
     *  Method for redirecting users if needed
     */
    private void redirectUser(FirebaseUser user) {
        if(user != null) {
            return;
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }

    /* Called when the user taps the View Homework button */
    public void homeworkButton(View view) {
        Intent intent = new Intent(this, HomeworkActivity.class);
        finish();
        startActivity(intent);
    }
}
