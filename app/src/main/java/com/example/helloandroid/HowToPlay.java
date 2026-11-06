package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        // 戻るボタン
        Button backButton = findViewById(R.id.back);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    HowToPlay.this,
                    StartDisplay.class
            );
            startActivity(intent);
            finish();
        });
    }
}