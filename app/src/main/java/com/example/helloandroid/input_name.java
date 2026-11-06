
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

        decideButton.setOnClickListener(v -> {

            String name = nameInput.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this,
                        "絵の名前を入力してください",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(
                    input_name.this, result_display.class);

            intent.putExtra("pictureName", name);

            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}

