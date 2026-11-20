package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        // プレイヤー人数の選択
        NumberPicker playerPicker =
                findViewById(R.id.playerPicker);

        // 周回数の選択
        NumberPicker roundPicker =
                findViewById(R.id.roundPicker);

        // 人数は2～6人
        playerPicker.setMinValue(2);
        playerPicker.setMaxValue(6);
        playerPicker.setValue(6);

        // 周回数は1～5周
        roundPicker.setMinValue(1);
        roundPicker.setMaxValue(5);
        roundPicker.setValue(1);

        // ゲームスタートボタン
        Button startButton =
                findViewById(R.id.Gamestart);

        startButton.setOnClickListener(v -> {

            // クリック音
            SoundPlayer.play(v.getContext());

            // 選択した人数
            int playerCount =
                    playerPicker.getValue();

            // 選択した周回数
            int roundCount =
                    roundPicker.getValue();

            // 最初のお題をランダムに決める
            String initialLetter =
                    InitialLetter.getRandomLetter();

            // お題表示画面へ移動
            Intent intent = new Intent(
                    PlayerSelect.this,
                    WordPrompt.class
            );

            // ゲーム情報を渡す
            intent.putExtra("playerCount", playerCount);
            intent.putExtra("roundCount", roundCount);
            intent.putExtra("currentTurn", 0);
            intent.putExtra("initialLetter", initialLetter);

            // 画面遷移
            startActivity(intent);
        });
    }
}
