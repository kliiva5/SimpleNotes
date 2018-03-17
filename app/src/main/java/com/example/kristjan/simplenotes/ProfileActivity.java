package com.example.kristjan.simplenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mAssignmentListView;

    // ArrayList and ArrayAdapter for the users's assignments
    private ArrayList<String> assignmentList = new ArrayList<>();
    private ArrayAdapter<String> assignmentArrayAdapter;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set onClick listeners for buttons
        findViewById(R.id.logout_button).setOnClickListener(this);

        // Initialize authentication
        mAuth = FirebaseAuth.getInstance();

        // Get the current user's ID in order to get a reference to their assignments
        String currentUserID = mAuth.getCurrentUser().getUid();

        // Initialize database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Assignments").child(currentUserID);

        // Get the required ListView for displaying the user's homework assignments
        mAssignmentListView = findViewById(R.id.assignemntView);

        // Set the defined ArrayAdapter to the ListView
        assignmentArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, assignmentList);
        mAssignmentListView.setAdapter(assignmentArrayAdapter);

        // Add a ChildEventListener to the Database reference in order to retrieve the required
        // Data in real time and display it
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Get the required data from the DataSnapshot
                String assignment_subject = dataSnapshot.child("Subject").getValue(String.class);
                String assignment_due_date = dataSnapshot.child("Due Date").getValue(String.class);

                // Add the acquired data to the list in order to display the user's assignments
                assignmentList.add(assignment_subject);

                // Notify the ArrayAdapter about the changes
                assignmentArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Remove the required data from the list
                String removed_assignment = dataSnapshot.getKey();
                assignmentList.remove(removed_assignment);

                // Notify about the changes
                assignmentArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Show the user an error
                Toast.makeText(ProfileActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

    /* Called when the user taps the New Homework button */
    public void homeworkButton(View view) {
        Intent intent = new Intent(this, NewHomeworkActivity.class);
        finish();
        startActivity(intent);
    }

    /* Called when the user taps the Log Out button */
    private void logOutButton(){
        mAuth.signOut();
        // User was signed out, redirect to the required activity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        redirectUser(currentUser);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.logout_button){
            logOutButton();
        }
    }
}
