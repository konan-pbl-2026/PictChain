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

        // ★この1行を追加！
        BgmPlayer.start(this);

        // ゲームスタートボタン
        Button startButton = findViewById(R.id.start);

        startButton.setOnClickListener(v -> {
            // ★ここでクリック音を鳴らす！
            SoundPlayer.play(v.getContext());

            Intent intent = new Intent(
                    StartDisplay.this,
                    PlayerSelect.class
            );
            startActivity(intent);
        });

        // 遊び方ボタン
        Button howToPlayButton = findViewById(R.id.HowToPlay);

        howToPlayButton.setOnClickListener(v -> {
            // ★ここでもクリック音を鳴らす！
            SoundPlayer.play(v.getContext());

            Intent intent = new Intent(
                    StartDisplay.this,
                    HowToPlay.class
            );
            startActivity(intent);
        });
    }
}