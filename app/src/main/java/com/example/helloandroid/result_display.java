package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class result_display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);
        Button resultButton = findViewById(R.id.button3);

        resultButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    result_display.this,
                    success_display.class
            );

            intent.putStringArrayListExtra(
                    "pictureNames",
                    getIntent().getStringArrayListExtra("pictureNames")
            );

            intent.putStringArrayListExtra(
                    "imagePaths",
                    getIntent().getStringArrayListExtra("imagePaths")
            );

            startActivity(intent);
        });
    }
}