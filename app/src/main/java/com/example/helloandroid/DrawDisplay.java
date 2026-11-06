
package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class DrawDisplay extends AppCompatActivity {

    private DrawingView drawingView;

    // dpをpxに変換
    private int dp(float value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density);
    }

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

        // 色を切り替える
        blackButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.BLACK));

        redButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.RED));

        yellowButton.setOnClickListener(v ->
                drawingView.setDrawColor(Color.YELLOW));

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

        // 現在のターン
        int currentTurn =
                getIntent().getIntExtra("currentTurn", 0);

        // 前の人の画像の保存場所
        String previousImagePath =
                getIntent().getStringExtra("previousImagePath");

        // 2人目以降なら前の人の絵を表示
        if (currentTurn > 0 && previousImagePath != null) {

            File imageFile = new File(previousImagePath);

            if (imageFile.exists()) {

                Bitmap bitmap =
                        BitmapFactory.decodeFile(previousImagePath);

                if (bitmap != null) {

                    ImageView previousImage = new ImageView(this);

                    previousImage.setImageBitmap(bitmap);

                    previousImage.setScaleType(
                            ImageView.ScaleType.FIT_CENTER);

                    // 黒い枠線
                    GradientDrawable border = new GradientDrawable();
                    border.setColor(Color.WHITE);
                    border.setStroke(dp(3), Color.BLACK);
                    border.setCornerRadius(dp(4));

                    previousImage.setBackground(border);

                    previousImage.setPadding(
                            dp(4), dp(4), dp(4), dp(4));

                    ConstraintLayout root =
                            (ConstraintLayout) drawingView.getParent();

                    // 普段の表示位置：色ボタンの下
                    ConstraintLayout.LayoutParams normalParams =
                            new ConstraintLayout.LayoutParams(
                                    dp(130),
                                    dp(100)
                            );

                    normalParams.startToStart =
                            ConstraintLayout.LayoutParams.PARENT_ID;

                    normalParams.endToEnd =
                            ConstraintLayout.LayoutParams.PARENT_ID;

                    normalParams.topToBottom = R.id.coller1;
                    normalParams.topMargin = dp(8);

                    root.addView(previousImage, normalParams);

                    // 押している間だけDrawingViewの上に表示
                    previousImage.setOnTouchListener((v, event) -> {

                        switch (event.getAction()) {

                            case MotionEvent.ACTION_DOWN:

                                // DrawingViewと同じ大きさにする
                                ConstraintLayout.LayoutParams bigParams =
                                        new ConstraintLayout.LayoutParams(
                                                0,
                                                0
                                        );

                                bigParams.startToStart =
                                        R.id.drawingView;

                                bigParams.endToEnd =
                                        R.id.drawingView;

                                bigParams.topToTop =
                                        R.id.drawingView;

                                bigParams.bottomToBottom =
                                        R.id.drawingView;

                                previousImage.setLayoutParams(bigParams);

                                // DrawingViewより手前に表示
                                previousImage.setElevation(dp(20));
                                previousImage.bringToFront();

                                return true;

                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:

                                // 元の位置と大きさに戻す
                                ConstraintLayout.LayoutParams smallParams =
                                        new ConstraintLayout.LayoutParams(
                                                dp(130),
                                                dp(100)
                                        );

                                smallParams.startToStart =
                                        ConstraintLayout.LayoutParams.PARENT_ID;

                                smallParams.endToEnd =
                                        ConstraintLayout.LayoutParams.PARENT_ID;

                                smallParams.topToBottom = R.id.coller1;
                                smallParams.topMargin = dp(8);

                                previousImage.setLayoutParams(smallParams);

                                previousImage.setElevation(0);

                                return true;
                        }

                        return true;
                    });
                }
            }
        }

        // 完了ボタン
        Button completeButton = findViewById(R.id.complete);

        completeButton.setOnClickListener(v -> {

            String imagePath = null;

            try {
                // 描画内容を画像として取得
                int width = drawingView.getWidth();
                int height = drawingView.getHeight();

                if (width > 0 && height > 0) {

                    Bitmap bitmap = Bitmap.createBitmap(
                            width,
                            height,
                            Bitmap.Config.ARGB_8888
                    );

                    Canvas canvas = new Canvas(bitmap);
                    drawingView.draw(canvas);

                    // 画像を保存
                    File imageFile = new File(
                            getFilesDir(),
                            "drawing_" + currentTurn + ".png"
                    );

                    try (FileOutputStream outputStream =
                                 new FileOutputStream(imageFile)) {

                        bitmap.compress(
                                Bitmap.CompressFormat.PNG,
                                100,
                                outputStream
                        );
                    }

                    imagePath = imageFile.getAbsolutePath();

                    bitmap.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // 名前入力画面へ移動
            Intent intent = new Intent(
                    DrawDisplay.this,
                    input_name.class
            );

            intent.putExtra("playerCount",
                    getIntent().getIntExtra("playerCount", 6));

            intent.putExtra("roundCount",
                    getIntent().getIntExtra("roundCount", 1));

            intent.putExtra("currentTurn", currentTurn);

            // 保存した画像の場所を渡す
            intent.putExtra("previousImagePath", imagePath);

            startActivity(intent);
        });
    }
}
