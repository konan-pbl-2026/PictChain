
package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

public class success_display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_display);

        TextView title = findViewById(R.id.resultTitle);
        TextView rate = findViewById(R.id.failurePosition);
        TextView message = findViewById(R.id.resultMessage);

        TextView icon = findViewById(R.id.resultIcon);
        TextView subTitle = findViewById(R.id.resultSubTitle);
        TextView description = findViewById(R.id.resultDescription);

        Button pictureButton = findViewById(R.id.button);

        int successCount = getIntent().getIntExtra(
                "successCount", 0);

        int totalCount = getIntent().getIntExtra(
                "totalCount", 0);

        int percentage = getIntent().getIntExtra(
                "percentage", 0);

        // 成功率表示
        rate.setText(
                percentage + "% COMPLETE\n"
                        + successCount + " / " + totalCount + " 成功"
        );

        // 100%の場合
        if (percentage == 100 && totalCount > 0) {

            title.setText("CHAIN COMPLETE");
            title.setTextColor(Color.rgb(32, 43, 60));

            message.setText("PERFECT!!");
            message.setTextColor(Color.rgb(33, 150, 210));

            icon.setText("🏆");

            subTitle.setText("しりとり完全成功！！");

            description.setText(
                    "全員の絵が最後までつながりました！"
            );

            rate.setTextColor(Color.rgb(33, 150, 210));

        } else {

            // 100%未満の場合
            title.setText("CHAIN DISCONNECTED");
            title.setTextColor(Color.rgb(229, 57, 53));

            message.setText("GAME OVER");
            message.setTextColor(Color.rgb(229, 57, 53));

            icon.setText("💥");

            subTitle.setText("しりとりが途切れました");

            if (percentage >= 75) {

                description.setText(
                        "おしい！！あと少しで完全成功でした！"
                );

            } else {

                description.setText(
                        "もう少し！次は最後までつなげよう！"
                );
            }

            rate.setTextColor(Color.rgb(229, 57, 53));
        }

        // 絵を見るボタン
        pictureButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    success_display.this,
                    PictureDisplay.class
            );

            intent.putStringArrayListExtra(
                    "pictureNames",
                    getIntent().getStringArrayListExtra("pictureNames")
            );

            intent.putStringArrayListExtra(
                    "imagePaths",
                    getIntent().getStringArrayListExtra("imagePaths")
            );

            startActivity(intent);
        });


        // もう一度遊ぶボタン
        Button retryButton = findViewById(R.id.retryButton);

        retryButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    success_display.this,
                    PlayerSelect.class
            );

            // 以前のゲーム画面を終了して新しく始める
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);
            finish();
        });

        // タイトルに戻るボタン
        Button homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    success_display.this,
                    MainActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);
            finish();
        });

    }
}


