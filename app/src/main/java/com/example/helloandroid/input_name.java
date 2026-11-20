package com.example.helloandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class input_name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        EditText nameInput = findViewById(R.id.nameInput);
        Button decideButton = findViewById(R.id.decideButton);
        Button backButton = findViewById(R.id.backButton);

        // ゲーム情報を受け取る
        int playerCount =
                getIntent().getIntExtra("playerCount", 6);

        int roundCount =
                getIntent().getIntExtra("roundCount", 1);

        int currentTurn =
                getIntent().getIntExtra("currentTurn", 0);

        // 最初のお題
        String initialLetter =
                getIntent().getStringExtra("initialLetter");

        // 今回描いた画像の保存場所
        String imagePath =
                getIntent().getStringExtra("previousImagePath");

        // 名前と画像パスを受け取る
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

        // 決定ボタン
        decideButton.setOnClickListener(v -> {

            SoundPlayer.play(v.getContext());

            String name = nameInput.getText()
                    .toString()
                    .trim()
                    .toLowerCase(Locale.ROOT);

            // 空欄チェック
            if (name.isEmpty()) {
                Toast.makeText(this,
                        "絵の名前を入力してください",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // アルファベットチェック
            if (!name.matches("[a-z]+")) {
                Toast.makeText(this,
                        "英語のアルファベットで入力してください",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // 1人目だけ最初のお題を判定
            if (currentTurn == 0) {

                if (initialLetter == null
                        || !name.startsWith(
                        initialLetter.toLowerCase(Locale.ROOT))) {

                    Toast.makeText(this,
                            initialLetter + "から始まる英単語を入力してください",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // 名前と画像を保存
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
                intent.putExtra("initialLetter", initialLetter);

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

                // お題も引き継ぐ
                intent.putExtra("initialLetter", initialLetter);

                intent.putStringArrayListExtra(
                        "pictureNames", pictureNames);

                intent.putStringArrayListExtra(
                        "imagePaths", imagePaths);

                startActivity(intent);
            }
        });

        // 描き直すボタン
        backButton.setOnClickListener(v -> {

            SoundPlayer.play(v.getContext());

            finish();
        });
    }
}
