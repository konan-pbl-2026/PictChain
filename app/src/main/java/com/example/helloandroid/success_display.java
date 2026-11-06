package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        Button pictureButton =
                findViewById(R.id.button);

        // 成功回数と総回数を受け取る
        int successCount = getIntent().getIntExtra(
                "successCount", 0);

        int totalCount = getIntent().getIntExtra(
                "totalCount", 0);

        // 成功率を計算
        double percentage = 0;

        if (totalCount > 0) {
            percentage =
                    (double) successCount / totalCount * 100;
        }

        int result = (int) Math.round(percentage);

        rate.setText(result + "%");

        // 成功率によって表示変更
        if (result >= 80) {

            title.setText("🎉 大成功！！ 🎉");
            message.setText("素晴らしい！最高の絵しりとり！");

        } else if (result >= 50) {

            title.setText("👏 よくできました！");
            message.setText("なかなかいい調子！");

        } else {

            title.setText("💪 おしい！！");
            message.setText("次はもっとつなげよう！");
        }

        pictureButton.setOnClickListener(v -> {

       Intent intent = new Intent(
               success_display.this,
              PictureDisplay.class
       );

       startActivity(intent);
        });
    }
}

