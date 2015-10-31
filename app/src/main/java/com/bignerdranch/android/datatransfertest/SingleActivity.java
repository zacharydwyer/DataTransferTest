package com.bignerdranch.android.datatransfertest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SingleActivity extends AppCompatActivity {

    private TextView sentTextTextView;
    private Button submitButton;
    private EditText stringInputEditText;

    private static final String TAG = "SingleActivity";

    private static final String KEY_EXTRA_SENT_TEXT = "com.bignerdranch.android.datatransfertest.singleactivity.sent_text";         // Key for text sent to this activity
    private static final String KEY_EXTRA_TEXT_TO_SEND = "com.bignerdranch.android.datatransfertest.singleactivity.text_to_send";   // Key for text that will be sent from this activity

    // Use this method to create a new Intent to start this Activity. It needs some text to output.
    public static Intent createIntent(Context packageContext, String textToSend) {
        Intent intent = new Intent(packageContext, SingleActivity.class);           // Intent used to start this activity
        intent.putExtra(KEY_EXTRA_SENT_TEXT, textToSend);                           // Add information this Activity will need
        return intent;                                                              // Return appropriate intent
    }

    // Use this method to decode the intent that was sent back after the user pressed the Submit button
    public static String getStringAnswerFromIntent(Intent dataPackage) {
       return dataPackage.getStringExtra(KEY_EXTRA_TEXT_TO_SEND);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        Log.d(TAG, "onCreate() called");

        // Get handle to widgets
        sentTextTextView = (TextView) findViewById(R.id.sent_text_textview);
        submitButton = (Button) findViewById(R.id.submit_button);
        stringInputEditText = (EditText) findViewById(R.id.string_input_edit_text);

        // Get string value from the intent that was used to start this activity
        String textFromIntent = getIntent().getStringExtra(KEY_EXTRA_SENT_TEXT);
        // Set value to text view

        Log.d(TAG, "Got string value from Intent, was " + textFromIntent);

        sentTextTextView.setText("Text received: " + textFromIntent);

        // Event handler for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Is the edit text empty?
                if (stringInputEditText.getText().toString().trim().isEmpty()) {
                    stringInputEditText.setError("Enter some text foo!");
                }
                else {
                    // Return the value
                    Intent dataPackage = new Intent();
                    dataPackage.putExtra(KEY_EXTRA_TEXT_TO_SEND, stringInputEditText.getText().toString());

                    Log.d(TAG, "Result being set. Value: " + stringInputEditText.getText().toString());

                    // Set the result
                    setResult(RESULT_OK, dataPackage);

                    // End this activity
                    finish();
                }
            }
        });
    }
}
