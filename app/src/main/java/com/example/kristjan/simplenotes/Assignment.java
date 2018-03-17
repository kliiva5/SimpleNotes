package com.example.kristjan.simplenotes;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kristjan on 14/02/2018.
 */

public class Assignment {

    public String assignment_subject;
    public String date_due;
    public String assignment_description;

    public Assignment() {
        // Default constructor required for calls to DataSnapshot.getValue(Assignment.class)
    }

    public Assignment(String assignment_subject, String date_due, String assignment_description){
        this.assignment_subject = assignment_subject;
        this.date_due = date_due;
        this.assignment_description = assignment_description;
    }

    public String getSubject() {
        return assignment_subject;
    }

    public String getDueDate() {
        return date_due;
    }

    public String getDescription() {
        return assignment_description;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Subject", assignment_subject);
        result.put("Due Date", date_due);
        result.put("Description", assignment_description);

        return result;
    }

}
