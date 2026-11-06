
package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;

public class PictureDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_display);

        LinearLayout pictureContainer =
                findViewById(R.id.pictureContainer);

        Button backButton =
                findViewById(R.id.backButton);

        // 絵の名前を受け取る
        ArrayList<String> pictureNames =
                getIntent().getStringArrayListExtra("pictureNames");

        // 名前がある場合
        if (pictureNames != null && !pictureNames.isEmpty()) {

            for (int i = 0; i < pictureNames.size(); i++) {

                TextView nameView = new TextView(this);

                nameView.setText(
                        (i + 1) + "人目：" + pictureNames.get(i)
                );

                nameView.setTextSize(20);
                nameView.setPadding(15, 20, 15, 20);

                pictureContainer.addView(nameView);
            }

        } else {

            TextView emptyView = new TextView(this);

            emptyView.setText("まだ絵が登録されていません");
            emptyView.setTextSize(18);

            pictureContainer.addView(emptyView);
        }

        // 戻るボタン
        backButton.setOnClickListener(v -> finish());
    }
}
