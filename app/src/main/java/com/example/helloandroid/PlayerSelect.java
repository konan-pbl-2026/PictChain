package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        // ゲームスタートボタン
        Button startButton = findViewById(R.id.Gamestart);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PlayerSelect.this,
                    DrawDisplay.class
            );
            startActivity(intent);
        });
    }
}