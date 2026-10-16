
package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartDisplay extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_display);

        // ゲームスタートボタン
        Button startButton = findViewById(R.id.start);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    StartDisplay.this,
                    PlayerSelect.class
            );
            startActivity(intent);
        });

        // 遊び方ボタン
        Button howToPlayButton = findViewById(R.id.HowToPlay);

        howToPlayButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    StartDisplay.this,
                    HowToPlay.class
            );
            startActivity(intent);
        });
    }
}

