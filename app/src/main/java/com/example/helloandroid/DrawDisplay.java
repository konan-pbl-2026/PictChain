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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DrawDisplay extends AppCompatActivity {

    private DrawingView drawingView;

    // ペン・消しゴムの初期サイズ
    private float penWidth = 12f;
    private float eraserWidth = 30f;

    // 現在選択されているツール
    private static final int TOOL_NONE = 0;
    private static final int TOOL_BLACK = 1;
    private static final int TOOL_RED = 2;
    private static final int TOOL_YELLOW = 3;
    private static final int TOOL_BLUE = 4;
    private static final int TOOL_GREEN = 5;
    private static final int TOOL_ERASER = 6;

    private int selectedTool = TOOL_NONE;

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
        int receivedPlayerCount =
                getIntent().getIntExtra("playerCount", 6);

        final int playerCount =
                receivedPlayerCount > 0 ? receivedPlayerCount : 6;

        int roundCount =
                getIntent().getIntExtra("roundCount", 1);

        int currentTurn =
                getIntent().getIntExtra("currentTurn", 0);

        // 最初のお題のアルファベット
        String initialLetter =
                getIntent().getStringExtra("initialLetter");

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
        ImageButton blackButton = findViewById(R.id.coller1);
        ImageButton redButton = findViewById(R.id.coller2);
        ImageButton yellowButton = findViewById(R.id.coller3);
        ImageButton blueButton = findViewById(R.id.coller4);
        ImageButton greenButton = findViewById(R.id.coller5);

        // 消しゴム・全消去
        ImageButton eraserButton = findViewById(R.id.eraser);
        ImageButton eraseAllButton = findViewById(R.id.erase);

        // 戻る・進む
        ImageButton backButton = findViewById(R.id.Back);
        ImageButton moveOnButton = findViewById(R.id.move_on);

        // 完了ボタン
        ImageButton completeButton = findViewById(R.id.complete);

        // ホームボタン
        Button homeButton = findViewById(R.id.homeButton);


        // =========================
        // 色ボタン
        // =========================

        blackButton.setOnClickListener(v -> {
            selectPen(TOOL_BLACK, Color.BLACK);
        });

        redButton.setOnClickListener(v -> {
            selectPen(TOOL_RED, Color.RED);
        });

        yellowButton.setOnClickListener(v -> {
            selectPen(TOOL_YELLOW, Color.YELLOW);
        });

        blueButton.setOnClickListener(v -> {
            selectPen(TOOL_BLUE, Color.BLUE);
        });

        // 緑ペン
        greenButton.setOnClickListener(v -> {
            selectPen(TOOL_GREEN, Color.rgb(0, 128, 0));
        });

        // =========================
        // 消しゴム
        // =========================

        eraserButton.setOnClickListener(v -> {
            selectEraser();
        });


        // =========================
        // 全消去
        // =========================

        eraseAllButton.setOnClickListener(v -> {
            SoundPlayer.play(v.getContext());
            drawingView.clearCanvas();
        });


        // =========================
        // 一手戻る
        // =========================

        backButton.setOnClickListener(v -> {
            SoundPlayer.play(v.getContext());
            drawingView.undo();
        });


        // =========================
        // 一手進む
        // =========================

        moveOnButton.setOnClickListener(v -> {
            SoundPlayer.play(v.getContext());
            drawingView.redo();
        });


        // =========================
        // ホームボタン
        // =========================

        homeButton.setOnClickListener(v -> {
            SoundPlayer.play(v.getContext());

            new AlertDialog.Builder(DrawDisplay.this)
                    .setTitle("確認")
                    .setMessage("本当にホームに戻りますか？")

                    .setPositiveButton("OK", (dialog, which) -> {
                        SoundPlayer.play(DrawDisplay.this);

                        Intent intent = new Intent(
                                DrawDisplay.this,
                                StartDisplay.class
                        );

                        intent.setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                        );

                        startActivity(intent);
                        finish();
                    })

                    .setNegativeButton("NO", (dialog, which) -> {
                        SoundPlayer.play(DrawDisplay.this);
                        dialog.dismiss();
                    })

                    .show();
        });


        // =========================
        // 前の人の画像
        // =========================

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
                            ImageView.ScaleType.FIT_CENTER
                    );

                    // 黒い枠線
                    GradientDrawable border = new GradientDrawable();
                    border.setColor(Color.WHITE);
                    border.setStroke(dp(3), Color.BLACK);
                    border.setCornerRadius(dp(4));

                    previousImage.setBackground(border);

                    previousImage.setPadding(
                            dp(4), dp(4), dp(4), dp(4)
                    );

                    ConstraintLayout root =
                            (ConstraintLayout) drawingView.getParent();

                    // 通常の表示位置
                    ConstraintLayout.LayoutParams normalParams =
                            new ConstraintLayout.LayoutParams(
                                    dp(90),
                                    dp(60)
                            );

                    normalParams.startToEnd = R.id.eraser;
                    normalParams.endToEnd =
                            ConstraintLayout.LayoutParams.PARENT_ID;
                    normalParams.topToTop = R.id.eraser;
                    normalParams.bottomToBottom = R.id.eraser;
                    normalParams.leftMargin = dp(8);
                    normalParams.rightMargin = dp(16);

                    root.addView(previousImage, normalParams);

                    // 押している間だけ大きく表示
                    previousImage.setOnTouchListener((v, event) -> {

                        switch (event.getAction()) {

                            case MotionEvent.ACTION_DOWN:

                                ConstraintLayout.LayoutParams bigParams =
                                        new ConstraintLayout.LayoutParams(
                                                0, 0
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

                                previousImage.setElevation(dp(20));
                                previousImage.bringToFront();

                                return true;

                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:

                                ConstraintLayout.LayoutParams smallParams =
                                        new ConstraintLayout.LayoutParams(
                                                dp(90),
                                                dp(60)
                                        );

                                smallParams.startToEnd = R.id.eraser;
                                smallParams.endToEnd =
                                        ConstraintLayout.LayoutParams.PARENT_ID;
                                smallParams.topToTop = R.id.eraser;
                                smallParams.bottomToBottom = R.id.eraser;
                                smallParams.leftMargin = dp(8);
                                smallParams.rightMargin = dp(16);

                                previousImage.setLayoutParams(smallParams);

                                previousImage.setElevation(0);

                                return true;
                        }

                        return true;
                    });
                }
            }
        }
        // =========================
        // 完了ボタン
        // =========================

        completeButton.setOnClickListener(v -> {
            SoundPlayer.play(v.getContext());

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

            // 最初のお題を引き継ぐ
            intent.putExtra("initialLetter", initialLetter);

            intent.putExtra("previousImagePath", imagePath);

            intent.putStringArrayListExtra(
                    "pictureNames", pictureNames
            );

            intent.putStringArrayListExtra(
                    "imagePaths", imagePaths
            );

            startActivity(intent);
        });
    }


    // =================================
    // ペンを選択する処理
    // =================================

    private void selectPen(int tool, int color) {

        SoundPlayer.play(this);

        // すでに選択されている色を押した場合
        if (selectedTool == tool) {

            // ペンのサイズ変更画面を開く
            showSizeDialog(false);

            return;
        }

        // 違う色が押された場合
        selectedTool = tool;

        drawingView.setDrawColor(color);
        drawingView.setWidth(penWidth);
    }


    // =================================
    // 消しゴムを選択する処理
    // =================================

    private void selectEraser() {

        SoundPlayer.play(this);

        // すでに消しゴムが選択されている場合
        if (selectedTool == TOOL_ERASER) {

            // 消しゴムのサイズ変更画面を開く
            showSizeDialog(true);

            return;
        }

        // 消しゴムに切り替える
        selectedTool = TOOL_ERASER;

        drawingView.setEraser();
        drawingView.setWidth(eraserWidth);
    }


    // =================================
    // ペン・消しゴムのサイズ変更画面
    // =================================

    private void showSizeDialog(boolean isEraser) {

        // 変更前のサイズを保存
        final float oldWidth =
                isEraser ? eraserWidth : penWidth;

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setPadding(
                dp(20), dp(15), dp(20), dp(10)
        );

        // サイズ表示
        TextView sizeText = new TextView(this);

        sizeText.setText(
                (isEraser ? "消しゴム" : "ペン") +
                        "の大きさ：" + (int) oldWidth
        );

        sizeText.setTextSize(18);
        sizeText.setTextColor(Color.BLACK);

        layout.addView(sizeText);

        // サイズ調節スライダー
        SeekBar seekBar = new SeekBar(this);

        seekBar.setMax(55);
        seekBar.setProgress((int) oldWidth - 5);

        layout.addView(seekBar);

        // 説明
        TextView explanation = new TextView(this);

        explanation.setText(
                "左に動かすと小さく、右に動かすと大きくなります。"
        );

        explanation.setTextSize(13);
        explanation.setTextColor(Color.GRAY);

        layout.addView(explanation);


        // スライダーを動かしたとき
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar,
                            int progress,
                            boolean fromUser) {

                        // 5～60の範囲にする
                        float newWidth = progress + 5;

                        if (isEraser) {

                            eraserWidth = newWidth;

                            drawingView.setEraser();

                        } else {

                            penWidth = newWidth;
                        }

                        drawingView.setWidth(newWidth);

                        // サイズ表示を更新
                        sizeText.setText(
                                (isEraser ? "消しゴム" : "ペン") +
                                        "の大きさ：" + (int) newWidth
                        );
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );
        // ダイアログを作成
        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                isEraser
                                        ? "消しゴムの大きさ"
                                        : "ペンの太さ"
                        )

                        .setView(layout)

                        .setPositiveButton("決定", (d, which) -> {

                            if (isEraser) {
                                drawingView.setEraser();
                                drawingView.setWidth(eraserWidth);
                            } else {
                                drawingView.setWidth(penWidth);
                            }
                        })

                        .setNegativeButton("キャンセル", (d, which) -> {

                            // 変更前のサイズに戻す
                            if (isEraser) {
                                eraserWidth = oldWidth;
                                drawingView.setEraser();
                            } else {
                                penWidth = oldWidth;
                            }

                            drawingView.setWidth(oldWidth);
                        })

                        .create();


        // ダイアログ外から閉じた場合も元に戻す
        dialog.setOnCancelListener(d -> {

            if (isEraser) {
                eraserWidth = oldWidth;
                drawingView.setEraser();
            } else {
                penWidth = oldWidth;
            }

            drawingView.setWidth(oldWidth);
        });

        dialog.show();
    }
}