
package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WordPrompt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_prompt);

        TextView letterText = findViewById(R.id.letterText);
        Button startButton = findViewById(R.id.startButton);

        // ゲーム情報を受け取る
        String letter =
                getIntent().getStringExtra("initialLetter");

        int playerCount =
                getIntent().getIntExtra("playerCount", 6);

        int roundCount =
                getIntent().getIntExtra("roundCount", 1);

        // アルファベットを表示
        letterText.setText(letter);

        // お絵かき開始
        startButton.setOnClickListener(v -> {

            SoundPlayer.play(v.getContext());

            Intent intent = new Intent(
                    WordPrompt.this,
                    DrawDisplay.class
            );

            intent.putExtra("playerCount", playerCount);
            intent.putExtra("roundCount", roundCount);
            intent.putExtra("currentTurn", 0);
            intent.putExtra("initialLetter", letter);

            startActivity(intent);
        });
    }
}

