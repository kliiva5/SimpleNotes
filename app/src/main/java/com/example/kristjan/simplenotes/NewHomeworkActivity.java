package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewHomeworkActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSubjectField;
    private EditText mDueDateField;
    private EditText mDescriptionField;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_homework);

        // Get the required Views
        mSubjectField = findViewById(R.id.subject_field);
        mDueDateField = findViewById(R.id.date_field);
        mDescriptionField = findViewById(R.id.homework_descritpion_field);

        // Add onClickListeners for buttons
        findViewById(R.id.add_assignment_button).setOnClickListener(this);

        // Initialize authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize database
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        redirectUser(currentUser);
    }

    private void redirectUser(FirebaseUser user) {
        if(user != null) {
            return;
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }

    /**
     *
     * @param subject - describes the subject that will receive an assignment
     * @param due_date -  describes the due date for the assignment
     * @param description
     *
     * Method for saving user assignments to Firebase Database
     */
    private void addNewHomework(String subject, String due_date, String description){
        // Check if the user entered the required data
        if(!checkFields()) {
            return;
        } else {
            // User filled the required fields, continue to save their homework assignment
            String currentUserID = mAuth.getCurrentUser().getUid();
            String key = mDatabase.child("Assignments").child(currentUserID).push().getKey();

            // Create a new DatabaseReference to keep previously added homeworks unmodified
            DatabaseReference mNewHomeworkRef = mDatabase.child("Assignments").child(currentUserID).child(key);
            Assignment assignment = new Assignment(subject, due_date, description);
            Map<String, Object> new_assignment = assignment.toMap();

            mNewHomeworkRef.updateChildren(new_assignment);

            // TODO: Add a dialogue to notify the user about successful execution
        }
    }

    private boolean checkFields(){

        boolean valid = true;

        return valid;
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.add_assignment_button) {
            addNewHomework(mSubjectField.getText().toString(), mDueDateField.getText().toString(), mDescriptionField.getText().toString());
        }
    }
}
