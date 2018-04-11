package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewHomeworkActivity extends AppCompatActivity implements NewHomeworkDialogFragment.NewHomeWorkListener {

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

    public void addHomeworkButton (View view) {
        DialogFragment newFragment = new NewHomeworkDialogFragment();
        newFragment.show(getSupportFragmentManager(), "Add Homework");
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

            // Create a new DatabaseReference to keep previously added homework unmodified
            DatabaseReference mNewHomeworkRef = mDatabase.child("Assignments").child(currentUserID).child(key);
            Assignment assignment = new Assignment(subject, due_date, description);
            Map<String, Object> new_assignment = assignment.toMap();

            mNewHomeworkRef.updateChildren(new_assignment);

            // Finish the current activity and start the ProfileActivity
            Intent intent = new Intent(this, ProfileActivity.class);
            finish();
            startActivity(intent);

            // Also show the user a Toast on a successful submission
            Toast.makeText(this, "Assignment added", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean checkFields(){

        boolean valid = true;

        // Get the information the user entered
        String due_date = mDueDateField.getText().toString();
        String subject = mSubjectField.getText().toString();
        String description = mDescriptionField.getText().toString();

        // Check if the user filled the required fields
        if(TextUtils.isEmpty(due_date)) {
            mDueDateField.setError("Due date is required");
            valid = false;
        } else {
            mDueDateField.setError(null);
        }

        if(TextUtils.isEmpty(subject)) {
            mSubjectField.setError("Subject is required");
            valid = false;
        } else {
            mSubjectField.setError(null);
        }

        if(TextUtils.isEmpty(description)) {
            mDescriptionField.setError("Assignment description is required");
            valid = false;
        } else {
            mDescriptionField.setError(null);
        }

        return valid;
    }

    /* Called when the user taps the Cancel button */
    public void cancelButton(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        finish();
        startActivity(intent);
    }

    /* Called when the user taps the Yes button on the dialog */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        addNewHomework(mSubjectField.getText().toString(), mDueDateField.getText().toString(), mDescriptionField.getText().toString());
    }

    /* Called when the user taps the No button ont he dialog */
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
