package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Locale;

public class result_display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);

        Button resultButton = findViewById(R.id.button3);

        resultButton.setOnClickListener(v -> {

            // クリック音
            SoundPlayer.play(v.getContext());

            // 単語リストを取得
            ArrayList<String> pictureNames =
                    getIntent().getStringArrayListExtra("pictureNames");

            ArrayList<String> imagePaths =
                    getIntent().getStringArrayListExtra("imagePaths");

            if (pictureNames == null) {
                pictureNames = new ArrayList<>();
            }

            if (imagePaths == null) {
                imagePaths = new ArrayList<>();
            }

            // 最初のお題を取得
            String initialLetter =
                    getIntent().getStringExtra("initialLetter");

            int successCount = 0;

            // ① 最初のお題と1人目の単語を判定
            if (!pictureNames.isEmpty()
                    && initialLetter != null
                    && pictureNames.get(0) != null) {

                String firstWord = pictureNames.get(0)
                        .trim()
                        .toLowerCase(Locale.ROOT);

                String letter = initialLetter
                        .trim()
                        .toLowerCase(Locale.ROOT);

                if (!firstWord.isEmpty()
                        && !letter.isEmpty()
                        && firstWord.startsWith(letter)) {

                    successCount++;
                }
            }

            // ② 2人目以降のしりとりを判定
            for (int i = 0; i < pictureNames.size() - 1; i++) {

                String first = pictureNames.get(i);
                String second = pictureNames.get(i + 1);

                if (first == null || second == null) {
                    continue;
                }

                first = first.trim().toLowerCase(Locale.ROOT);
                second = second.trim().toLowerCase(Locale.ROOT);

                if (!first.isEmpty() && !second.isEmpty()) {

                    char last = first.charAt(first.length() - 1);
                    char next = second.charAt(0);

                    if (last == next) {
                        successCount++;
                    }
                }
            }

            // ③ 判定回数
            int totalCount = pictureNames.size();

            // ④ 成功率
            int percentage = 0;

            if (totalCount > 0) {
                percentage = Math.round(
                        (float) successCount / totalCount * 100
                );
            }

            // 結果画面へ移動
            Intent intent = new Intent(
                    result_display.this,
                    success_display.class
            );

            intent.putExtra("successCount", successCount);
            intent.putExtra("totalCount", totalCount);
            intent.putExtra("percentage", percentage);

            intent.putExtra("initialLetter", initialLetter);

            intent.putStringArrayListExtra(
                    "pictureNames", pictureNames);

            intent.putStringArrayListExtra(
                    "imagePaths", imagePaths);

            startActivity(intent);
        });
    }
}
