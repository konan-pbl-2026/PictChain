
package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.ViewGroup;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class PictureDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_display);

        LinearLayout container =
                findViewById(R.id.pictureContainer);

        Button backButton =
                findViewById(R.id.pictureBackButton);

        // 絵の名前と画像パスを受け取る
        ArrayList<String> pictureNames =
                getIntent().getStringArrayListExtra("pictureNames");

        ArrayList<String> imagePaths =
                getIntent().getStringArrayListExtra("imagePaths");

        if (pictureNames == null || imagePaths == null) {
            return;
        }

        // 絵を順番に表示
        int count = Math.min(
                pictureNames.size(),
                imagePaths.size()
        );

        for (int i = 0; i < count; i++) {

            // 絵の名前
            TextView nameText = new TextView(this);
            nameText.setText((i + 1) + "枚目：" + pictureNames.get(i));
            nameText.setTextSize(20);
            nameText.setPadding(0, 20, 0, 10);

            container.addView(nameText);

            // 画像
            ImageView imageView = new ImageView(this);

            imageView.setImageBitmap(
                    BitmapFactory.decodeFile(imagePaths.get(i))
            );

            imageView.setScaleType(
                    ImageView.ScaleType.FIT_CENTER
            );

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            600
                    );

            params.bottomMargin = 30;

            container.addView(imageView, params);
        }

        // 戻るボタン
        backButton.setOnClickListener(v -> finish());
    }
}

