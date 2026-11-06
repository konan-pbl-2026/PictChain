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

    private static class Stroke {
        Path path;
        Paint paint;

        Stroke(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

    private List<Stroke> strokes = new ArrayList<>();
    private List<Stroke> undoneStrokes = new ArrayList<>();

    // ★追加：全消去した直前の状態を保存しておく変数（全消去のUndo用）
    private List<Stroke> clearedStrokesSnapshot = null;

    private Path currentPath;
    private Paint currentPaint;

    private int currentColor = Color.BLACK;
    private float currentWidth = 12f;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Stroke s : strokes) {
            canvas.drawPath(s.path, s.paint);
        }

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
                // 新しい線を描き始めたら、全消去の復元データはクリアする
                clearedStrokesSnapshot = null;
                undoneStrokes.clear();

                currentPath = new Path();
                currentPath.moveTo(x, y);

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

    public void setColor(int color) {
        currentColor = color;
    }

    public void setDrawColor(int color) {
        currentColor = color;
    }

    public void setWidth(float width) {
        currentWidth = width;
    }

    public void setEraser() {
        currentColor = Color.WHITE;
    }

    // --- 変更：全消去（消す前の状態をスナップショットに保存する） ---
    public void clearCanvas() {
        if (!strokes.isEmpty()) {
            clearedStrokesSnapshot = new ArrayList<>(strokes); // 状態を保持
            strokes.clear();
            undoneStrokes.clear();
            currentPath = null;
            currentPaint = null;
            invalidate();
        }
    }

    // --- 変更：一手戻る（全消去の取り消しにも対応させる） ---
    public void undo() {
        if (!strokes.isEmpty()) {
            Stroke lastStroke = strokes.remove(strokes.size() - 1);
            undoneStrokes.add(lastStroke);
            invalidate();
        } else if (clearedStrokesSnapshot != null) {
            // 画面が真っ白な状態で「戻る」が押されたら、全消去前の絵を復活させる！
            strokes = clearedStrokesSnapshot;
            clearedStrokesSnapshot = null;
            invalidate();
        }
    }

    public void redo() {
        if (!undoneStrokes.isEmpty()) {
            Stroke lastStroke = undoneStrokes.remove(undoneStrokes.size() - 1);
            strokes.add(lastStroke);
            invalidate();
        }
    }
}