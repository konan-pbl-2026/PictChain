
package com.example.helloandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class input_name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        EditText nameInput = findViewById(R.id.nameInput);
        Button decideButton = findViewById(R.id.decideButton);
        Button backButton = findViewById(R.id.backButton);

        // DrawDisplayからゲーム情報を受け取る
        int playerCount = getIntent().getIntExtra("playerCount", 6);
        int roundCount = getIntent().getIntExtra("roundCount", 1);
        int currentTurn = getIntent().getIntExtra("currentTurn", 0);

        // 前の人の画像の保存場所を受け取る
        String previousImagePath =
                getIntent().getStringExtra("previousImagePath");

        decideButton.setOnClickListener(v -> {

            String name = nameInput.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this,
                        "絵の名前を入力してください",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // 今回のターンが終了
            int nextTurn = currentTurn + 1;

            // 全員のターンが終了したか確認
            if (nextTurn >= playerCount * roundCount) {

                // 結果発表画面へ
                Intent intent = new Intent(
                        input_name.this,
                        result_display.class
                );

                intent.putExtra("pictureName", name);
                intent.putExtra("playerCount", playerCount);
                intent.putExtra("roundCount", roundCount);

                startActivity(intent);

            } else {

                // 次の人のお絵描き画面へ
                Intent intent = new Intent(
                        input_name.this,
                        DrawDisplay.class
                );

                intent.putExtra("pictureName", name);
                intent.putExtra("playerCount", playerCount);
                intent.putExtra("roundCount", roundCount);
                intent.putExtra("currentTurn", nextTurn);

                // 前の人の画像の保存場所を次の画面に渡す
                intent.putExtra("previousImagePath", previousImagePath);

                startActivity(intent);
            }
        });

        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}
