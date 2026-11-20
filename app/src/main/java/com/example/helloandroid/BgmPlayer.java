package com.example.helloandroid;

import android.content.Context;
import android.media.MediaPlayer;

public class BgmPlayer {
    private static MediaPlayer mediaPlayer;

    // BGMの再生開始（ループ再生）
    public static void start(Context context) {
        if (mediaPlayer == null) {
            // res/raw/bgm.ogg を読み込む
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.bgm);
            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true); // ずっと繰り返し再生する設定
            }
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    // BGMの停止（必要に応じて）
    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}