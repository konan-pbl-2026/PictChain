package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

public class DrawDisplay extends AppCompatActivity {
    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 班員が作った画面を表示
        setContentView(R.layout.activity_draw_display);

        // 描画エリアを取得
        drawingView = findViewById(R.id.drawingView);

        // 色ボタン
        Button blackButton = findViewById(R.id.coller1);
        Button redButton = findViewById(R.id.coller2);
        Button yellowButton = findViewById(R.id.coller3);
        Button blueButton = findViewById(R.id.coller4);

        // 消しゴム・全消去ボタン
        Button eraserButton = findViewById(R.id.eraser);
        Button eraseAllButton = findViewById(R.id.erase);

        // 色を切り替える
        blackButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.BLACK));

        redButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.RED));

        yellowButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.YELLOW));

        blueButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.BLUE));

        // 消しゴム（白ペン）に切り替える
        eraserButton.setOnClickListener(v ->
                drawingView.setEraser());

        // 画面を全消去する
        eraseAllButton.setOnClickListener(v ->
                drawingView.clearCanvas());
    }
}