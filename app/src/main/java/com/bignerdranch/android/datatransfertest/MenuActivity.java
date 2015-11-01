package com.bignerdranch.android.datatransfertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    // Tag
    private final String TAG = "MenuActivity";

    // Widgets
    private Button mStartSingleValueActivityButton;
    private Button mStartMultiValueActivityButton;
    private EditText mTextToSendToActivityEditText;

    // Request codes for activities
    private final int REQUEST_CODE_SINGLE_ACTIVITY = 0;
    private final int REQUEST_CODE_MULTI_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Assign widgets from layout
        mStartSingleValueActivityButton = (Button) findViewById(R.id.single_value_activity_button);
        mStartMultiValueActivityButton = (Button) findViewById(R.id.multi_value_activity_button);
        mTextToSendToActivityEditText = (EditText) findViewById(R.id.text_to_send_to_activity_edit_text);

        /* on click listeners */

        // Start single value activity
        mStartSingleValueActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStartingSingleActivity();
            }
        });

        // Start multi value activity
        mStartMultiValueActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // Handles starting the single activity
    private void handleStartingSingleActivity() {

        // If the text in the "text to send to activity edittext" is empty...
        if (mTextToSendToActivityEditText.getText().toString().trim().isEmpty()) {

            // Create alert dialog builder
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // Set its preferences
            alertDialogBuilder.setTitle("Confirm");
            alertDialogBuilder.setMessage("Text to send to activity is empty. Continue?");

            // What to do when user presses Yes
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Start the activity
                    startSingleActivity();

                    // Dimiss dialog
                    dialog.dismiss();
                }
            });

            // What to do when user presses No
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Dismiss dialog
                    dialog.dismiss();
                }
            });

            // Create the alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // Show the dialog
            alertDialog.show();
        }
        else {
            // Just start the activity without the dialog
            startSingleActivity();
        }
    }

    // Actually start the single activity - called from handleStartingSingleActivity
    private void startSingleActivity() {

        // Create intent for singleActivity, send it the text you wanted to send it
        Intent intentToStartSingleActivity = SingleActivity.createIntent(MenuActivity.this, mTextToSendToActivityEditText.getText().toString());

        // Start activity for a result
        startActivityForResult(intentToStartSingleActivity, REQUEST_CODE_SINGLE_ACTIVITY);
    }

    // Handle what happens when this Activity is given a result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If activity was canceled...
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "The activity was canceled.", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode == RESULT_OK) {

            // Which one of the requests were fulfilled?
            switch(requestCode) {
                case REQUEST_CODE_SINGLE_ACTIVITY:

                    // Decode the data
                    String randomStringFromSingleActivity = SingleActivity.getStringAnswerFromIntent(data);

                    // Make a toast from data
                    Toast.makeText(this, "Data I received: " + randomStringFromSingleActivity, Toast.LENGTH_SHORT).show();

                    break;
                case REQUEST_CODE_MULTI_ACTIVITY:

                    break;
                default:
                    Toast.makeText(this, "No idea who's sending this data! Don't know what to do with it!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
