package com.example.helloandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {
    // 描いた線（パスとペンのセット）を保存するクラス
    private static class Stroke {
        Path path;
        Paint paint;
        Stroke(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

    private List<Stroke> strokes = new ArrayList<>();
    private Path currentPath;
    private Paint currentPaint;

    private int currentColor = Color.BLACK; // 初期の色：黒
    private float currentWidth = 12f;       // 初期の太さ

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // これまでに描いたすべての線を描画する
        for (Stroke s : strokes) {
            canvas.drawPath(s.path, s.paint);
        }
        // 現在描いている最中の線を描画する
        if (currentPath != null) {
            canvas.drawPath(currentPath, currentPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath = new Path();
                currentPath.moveTo(x, y);

                // 描き始めの瞬間のペン（色・太さ）を固定する
                currentPaint = new Paint();
                currentPaint.setColor(currentColor);
                currentPaint.setAntiAlias(true);
                currentPaint.setStrokeWidth(currentWidth);
                currentPaint.setStyle(Paint.Style.STROKE);
                currentPaint.setStrokeJoin(Paint.Join.ROUND);
                currentPaint.setStrokeCap(Paint.Cap.ROUND);
                break;

            case MotionEvent.ACTION_MOVE:
                if (currentPath != null) {
                    currentPath.lineTo(x, y);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (currentPath != null) {
                    strokes.add(new Stroke(currentPath, currentPaint));
                    currentPath = null;
                }
                break;
        }
        invalidate(); // 画面を再描画
        return true;
    }

    // --- 外部から呼び出す用の便利メソッドたち ---

    // ペンの色を変える（例：Color.RED など）
    public void setColor(int color) {
        currentColor = color;
    }

    // ペンの太さを変える
    public void setWidth(float width) {
        currentWidth = width;
    }

    // 消しゴム（背景が白なら、白ペンにするのが一番シンプルで確実です！）
    public void setEraser() {
        currentColor = Color.WHITE;
    }

    // 全消去
    public void clearCanvas() {
        strokes.clear();
        currentPath = null;
        invalidate();
    }
}