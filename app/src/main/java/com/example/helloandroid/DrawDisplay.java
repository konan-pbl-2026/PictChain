
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

        // 描画画面を表示
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

        // 戻る・進むボタン
        Button backButton = findViewById(R.id.Back);
        Button moveOnButton = findViewById(R.id.move_on);

        // 黒色
        blackButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.BLACK));

        // 赤色
        redButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.RED));

        // 黄色
        yellowButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.YELLOW));

        // 青色
        blueButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.BLUE));

        // 消しゴム
        eraserButton.setOnClickListener(v ->
                drawingView.setEraser());

        // 全消去
        eraseAllButton.setOnClickListener(v ->
                drawingView.clearCanvas());

        // 一手戻る
        backButton.setOnClickListener(v ->
                drawingView.undo());

        // 一手進む
        moveOnButton.setOnClickListener(v ->
                drawingView.redo());
    }
}
