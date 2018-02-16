package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeworkActivity extends AppCompatActivity {

    private TextView mDescriptionField;
    private TextView mDueDateField;
    private TextView mStatusField;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        // Initialize views
       // mDescriptionField = findViewById(R.id.description_field);
       // mStatusField = findViewById(R.id.status_field);
       // mDueDateField = findViewById(R.id.due_date_field);

        // Initialize database
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Assignments");

        // Initialize authentication
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart(){
        super.onStart();
        // Check if the user is already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        redirectUser(currentUser);

        // Add value event listener to the post
        ValueEventListener assignmentListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the Assignment object and use the values to update displayed information
                Assignment assignment = dataSnapshot.getValue(Assignment.class);
                mDescriptionField.setText(assignment.assignment_description);
                mDueDateField.setText(assignment.date_due);
                mStatusField.setText(assignment.assignment_status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting the needed data failed, log a message
                Log.w("Error", "Retrieving data failed", databaseError.toException());
                // Show a Toast message to the user
                Toast.makeText(HomeworkActivity.this, "Failed to load assignment",
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void redirectUser(FirebaseUser user) {

        if(user != null) {
            // Stop further code execution, since user is already logged in
            return;
        } else {
            // Redirect the user to the required Activity
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }

    /* Method for checking (ticking) completed assignments */
    private void completeAssignment(){
        // Retrieve the current user's ID for looking up their assignment
        String userID = mAuth.getCurrentUser().getUid();
        // Retrieve the required ID of the assignment to update it's status
        String key = mDatabase.child(userID).getKey();
        mDatabase.child(userID).child(key).child("status").setValue("Completed");
    }

}
