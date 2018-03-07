package com.example.kristjan.simplenotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Kristjan on 28/02/2018.
 */

public class NewHomeworkDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NewHomeWorkListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NewHomeWorkListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NewHomeWorkListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
            + " must implement NewHomeworkListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new Alert Dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Setting the title for the dialog which will be displayed to the user
        builder.setMessage("Confirmation")
            // Set the positive button's title and functionality
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Pass the instance of this dialog fragment to the required activity
                    mListener.onDialogPositiveClick(NewHomeworkDialogFragment.this);
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mListener.onDialogNegativeClick(NewHomeworkDialogFragment.this);
                }
            });

        // Return the built alert dialog
        return builder.create();
    }

}
