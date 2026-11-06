package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;

public class result_display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);

        Button resultButton = findViewById(R.id.button3);

        resultButton.setOnClickListener(v -> {

            ArrayList<String> pictureNames =
                    getIntent().getStringArrayListExtra("pictureNames");

            ArrayList<String> imagePaths =
                    getIntent().getStringArrayListExtra("imagePaths");

            if (pictureNames == null) {
                pictureNames = new ArrayList<>();
            }

            int successCount = 0;

            for (int i = 0; i < pictureNames.size() - 1; i++) {

                String first = pictureNames.get(i)
                        .trim().toLowerCase();

                String second = pictureNames.get(i + 1)
                        .trim().toLowerCase();

                if (!first.isEmpty() && !second.isEmpty()) {

                    char last = first.charAt(first.length() - 1);
                    char next = second.charAt(0);

                    if (last == next) {
                        successCount++;
                    }
                }
            }

            int totalCount = Math.max(0, pictureNames.size() - 1);

            int percentage = 0;

            if (totalCount > 0) {
                percentage = Math.round(
                        (float) successCount / totalCount * 100
                );
            }

            Intent intent = new Intent(
                    result_display.this,
                    success_display.class
            );

            intent.putExtra("successCount", successCount);
            intent.putExtra("totalCount", totalCount);
            intent.putExtra("percentage", percentage);

            intent.putStringArrayListExtra(
                    "pictureNames", pictureNames);

            intent.putStringArrayListExtra(
                    "imagePaths", imagePaths);

            startActivity(intent);
        });
    }
}
