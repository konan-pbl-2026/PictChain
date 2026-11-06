package com.example.helloandroid;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int clickSoundId;
    private static boolean isLoaded = false;

    // 初期化
    public static void init(Context context) {
        if (soundPool == null) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();

            clickSoundId = soundPool.load(context, R.raw.button_click, 1);

            soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
                if (status == 0) {
                    isLoaded = true;
                }
            });
        }
    }

    // クリック音を鳴らす
    public static void play(Context context) {
        if (soundPool == null) {
            init(context);
        }
        if (isLoaded) {
            soundPool.play(clickSoundId, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }
}