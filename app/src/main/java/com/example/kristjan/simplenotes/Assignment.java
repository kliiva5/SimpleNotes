package com.example.kristjan.simplenotes;

import com.google.firebase.database.Exclude;

/**
 * Created by Kristjan on 14/02/2018.
 */

public class Assignment {

    private String assignment_id;
    private String date_due;
    private String date_added;
    private String assignment_status;
    private String assignment_description;

    public Assignment() {
        // Default constructor required for calls to DataSnapshot.getValue(Assignment.class)
    }

    public Assignment(String assignment_id, String date_due, String date_added, String assignment_status, String assignment_description){
        this.assignment_id = assignment_id;
        this.date_due = date_due;
        this.date_added = date_added;
        this.assignment_status = assignment_status;
        this.assignment_description = assignment_description;
    }

}
