package com.foodapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.R;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private static final String API_KEY = "AIzaSyDUVsy6FiG3oiQkDgbDwjOIDLB_7Xm5DXU";
    private static final String MODEL_NAME = "gemini-1.5-flash";

    private Button sendButton;
    private EditText inputField;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        // Initialize views
        sendButton = findViewById(R.id.sendBtn);
        inputField = findViewById(R.id.editTextInput);
        resultTextView = findViewById(R.id.resultTextView);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSendChat();
            }
        });
    }

    public void buttonSendChat() {
        String userInput = inputField.getText().toString();
        if (userInput.isEmpty()) {
            Log.w(TAG, "User input is empty.");
            return;
        }

        GenerativeModel gm = new GenerativeModel(MODEL_NAME, API_KEY);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content = new Content.Builder().addText(userInput).build();

        Executor executor = Executors.newSingleThreadExecutor();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                Log.d(TAG, resultText);
                runOnUiThread(() -> {
                    resultTextView.setText(resultText);
                    inputField.setText("");
                });
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Error generating content", t);
            }
        }, executor);
    }
}
