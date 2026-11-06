
package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AlertDialog;

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
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

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

        // 描画画面
        setContentView(R.layout.activity_draw_display);

        // 描画エリア
        drawingView = findViewById(R.id.drawingView);

        // ゲーム情報
        int playerCount =
                getIntent().getIntExtra("playerCount", 6);

        int roundCount =
                getIntent().getIntExtra("roundCount", 1);

        int currentTurn =
                getIntent().getIntExtra("currentTurn", 0);

        // 人数・週数を計算
        int playerNumber =
                (currentTurn % playerCount) + 1;

        int roundNumber =
                (currentTurn / playerCount) + 1;

        // 人数・週数を表示
        TextView playerText =
                findViewById(R.id.playerText);

        TextView roundText =
                findViewById(R.id.roundText);

        playerText.setText(playerNumber + "人目");
        roundText.setText(roundNumber + "週目");

        // 色ボタン
        Button blackButton = findViewById(R.id.coller1);
        Button redButton = findViewById(R.id.coller2);
        Button yellowButton = findViewById(R.id.coller3);
        Button blueButton = findViewById(R.id.coller4);

        // 消しゴム・全消去
        Button eraserButton = findViewById(R.id.eraser);
        Button eraseAllButton = findViewById(R.id.erase);

        // 戻る・進む
        Button backButton = findViewById(R.id.Back);
        Button moveOnButton = findViewById(R.id.move_on);

        // ホームボタン
        Button homeButton = findViewById(R.id.homeButton);

        // 色変更
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

        // ホームボタン
        homeButton.setOnClickListener(v -> {

            new AlertDialog.Builder(DrawDisplay.this)
                    .setTitle("確認")
                    .setMessage("本当にホームに戻りますか？")

                    .setPositiveButton("OK", (dialog, which) -> {

                        Intent intent = new Intent(
                                DrawDisplay.this,
                                StartDisplay.class
                        );

                        // 途中のゲーム画面を終了してホームへ
                        intent.setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                        );

                        startActivity(intent);
                        finish();

                    })

                    .setNegativeButton("NO", (dialog, which) -> {
                        dialog.dismiss();
                    })

                    .show();
        });

        // 前の人の画像
        String previousImagePath =
                getIntent().getStringExtra("previousImagePath");

        ArrayList<String> receivedNames =
                getIntent().getStringArrayListExtra("pictureNames");

        ArrayList<String> receivedPaths =
                getIntent().getStringArrayListExtra("imagePaths");

        final ArrayList<String> pictureNames =
                receivedNames != null
                        ? receivedNames
                        : new ArrayList<>();

        final ArrayList<String> imagePaths =
                receivedPaths != null
                        ? receivedPaths
                        : new ArrayList<>();

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

                    // 通常の表示位置
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

                    // 押している間だけ大きく表示
                    previousImage.setOnTouchListener((v, event) -> {

                        switch (event.getAction()) {

                            case MotionEvent.ACTION_DOWN:

                                ConstraintLayout.LayoutParams bigParams =
                                        new ConstraintLayout.LayoutParams(
                                                0, 0);

                                bigParams.startToStart =
                                        R.id.drawingView;

                                bigParams.endToEnd =
                                        R.id.drawingView;

                                bigParams.topToTop =
                                        R.id.drawingView;

                                bigParams.bottomToBottom =
                                        R.id.drawingView;

                                previousImage.setLayoutParams(bigParams);

                                previousImage.setElevation(dp(20));
                                previousImage.bringToFront();

                                return true;

                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:

                                ConstraintLayout.LayoutParams smallParams =
                                        new ConstraintLayout.LayoutParams(
                                                dp(130),
                                                dp(100));

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

                // 描画内容を画像にする
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

            // 名前入力画面へ
            Intent intent = new Intent(
                    DrawDisplay.this,
                    input_name.class
            );

            intent.putExtra("playerCount", playerCount);
            intent.putExtra("roundCount", roundCount);
            intent.putExtra("currentTurn", currentTurn);

            intent.putExtra("previousImagePath", imagePath);

            intent.putStringArrayListExtra(
                    "pictureNames", pictureNames);

            intent.putStringArrayListExtra(
                    "imagePaths", imagePaths);

            startActivity(intent);
        });
    }
}
