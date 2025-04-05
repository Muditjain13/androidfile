package com.example.files;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Set up pulse animation


        // ✅ Handle dynamic message input for NFC
        EditText messageBox = findViewById(R.id.messageBox);
        Button sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            String userMessage = messageBox.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                MyHostApduService.setMessage(userMessage);
                Toast.makeText(this, "Message set: " + userMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
