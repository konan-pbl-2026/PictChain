package com.example.helloandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;

public class SoundButton extends AppCompatButton {

    private OnClickListener originalListener;

    public SoundButton(Context context) {
        super(context);
        init();
    }

    public SoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // ボタンが押されたら自動で音を鳴らしてから、本来の処理を動かす
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 自動で音を鳴らす！
                SoundPlayer.play(getContext());

                // もともと予定されていたボタンの処理があれば実行する
                if (originalListener != null) {
                    originalListener.onClick(v);
                }
            }
        });
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        // 設定されたリスナーを一旦こちらで保持する
        this.originalListener = l;
    }
}