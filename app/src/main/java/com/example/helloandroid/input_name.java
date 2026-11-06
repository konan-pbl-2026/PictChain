package com.example.helloandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class input_name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        EditText nameInput = findViewById(R.id.nameInput);
        Button decideButton = findViewById(R.id.decideButton);
        Button backButton = findViewById(R.id.backButton);

        // ゲーム情報を受け取る
        int playerCount = getIntent().getIntExtra("playerCount", 6);
        int roundCount = getIntent().getIntExtra("roundCount", 1);
        int currentTurn = getIntent().getIntExtra("currentTurn", 0);

        // 今回描いた画像の保存場所
        String imagePath =
                getIntent().getStringExtra("previousImagePath");

        // これまでの名前と画像パスを受け取る
        ArrayList<String> receivedNames =
                getIntent().getStringArrayListExtra("pictureNames");

        ArrayList<String> receivedPaths =
                getIntent().getStringArrayListExtra("imagePaths");

        final ArrayList<String> pictureNames =
                receivedNames != null
                        ? receivedNames
                        : new ArrayList<>();

        final ArrayList<String> imagePaths =
                receivedPaths != null
                        ? receivedPaths
                        : new ArrayList<>();

        decideButton.setOnClickListener(v -> {

            String name = nameInput.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this,
                        "絵の名前を入力してください",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // 今回の名前と画像を保存
            pictureNames.add(name);

            if (imagePath != null) {
                imagePaths.add(imagePath);
            }

            int nextTurn = currentTurn + 1;

            // 全員のターンが終了した場合
            if (nextTurn >= playerCount * roundCount) {

                Intent intent = new Intent(
                        input_name.this,
                        result_display.class
                );

                intent.putStringArrayListExtra(
                        "pictureNames", pictureNames);

                intent.putStringArrayListExtra(
                        "imagePaths", imagePaths);

                intent.putExtra("playerCount", playerCount);
                intent.putExtra("roundCount", roundCount);

                startActivity(intent);

            } else {

                // 次のプレイヤーへ
                Intent intent = new Intent(
                        input_name.this,
                        DrawDisplay.class
                );

                intent.putExtra("playerCount", playerCount);
                intent.putExtra("roundCount", roundCount);
                intent.putExtra("currentTurn", nextTurn);

                intent.putExtra("previousImagePath", imagePath);

                intent.putStringArrayListExtra(
                        "pictureNames", pictureNames);

                intent.putStringArrayListExtra(
                        "imagePaths", imagePaths);

                startActivity(intent);
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}
