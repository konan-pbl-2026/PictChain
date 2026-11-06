
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

    // 描いた線を保存するクラス
    private static class Stroke {
        Path path;
        Paint paint;

        Stroke(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

    // 描いた線の履歴
    private List<Stroke> strokes = new ArrayList<>();

    // 戻した線の履歴
    private List<Stroke> undoneStrokes = new ArrayList<>();

    private Path currentPath;
    private Paint currentPaint;

    // 初期設定
    private int currentColor = Color.BLACK;
    private float currentWidth = 12f;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 描画処理
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 保存されている線をすべて描画
        for (Stroke s : strokes) {
            canvas.drawPath(s.path, s.paint);
        }

        // 描画中の線
        if (currentPath != null) {
            canvas.drawPath(currentPath, currentPaint);
        }
    }

    // タッチ操作
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                // 新しい線を描いたら進む履歴を消す
                undoneStrokes.clear();

                currentPath = new Path();
                currentPath.moveTo(x, y);

                // 描き始めのペン設定を保存
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
                    currentPaint = null;
                }

                break;
        }

        invalidate();
        return true;
    }

    // -----------------------------
    // ペンの色を変更
    // -----------------------------
    public void setColor(int color) {
        currentColor = color;
    }

    public void setDrawColor(int color) {
        currentColor = color;
    }

    // -----------------------------
    // ペンの太さを変更
    // -----------------------------
    public void setWidth(float width) {
        currentWidth = width;
    }

    // -----------------------------
    // 消しゴム
    // -----------------------------
    public void setEraser() {
        currentColor = Color.WHITE;
    }

    // -----------------------------
    // 全消去
    // -----------------------------
    public void clearCanvas() {
        strokes.clear();
        undoneStrokes.clear();
        currentPath = null;
        currentPaint = null;
        invalidate();
    }

    // -----------------------------
    // 一手戻る（Undo）
    // -----------------------------
    public void undo() {

        if (!strokes.isEmpty()) {

            Stroke lastStroke =
                    strokes.remove(strokes.size() - 1);

            undoneStrokes.add(lastStroke);

            invalidate();
        }
    }

    // -----------------------------
    // 一手進む（Redo）
    // -----------------------------
    public void redo() {

        if (!undoneStrokes.isEmpty()) {

            Stroke lastStroke =
                    undoneStrokes.remove(undoneStrokes.size() - 1);

            strokes.add(lastStroke);

            invalidate();
        }
    }
}
