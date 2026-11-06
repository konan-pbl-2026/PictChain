
package com.example.helloandroid;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HowToPlay extends AppCompatActivity {

    private int dp(float value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面全体
        FrameLayout root = new FrameLayout(this);

        // 明るいブルーとパープルの背景
        GradientDrawable background = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(210, 237, 255),
                        Color.rgb(232, 229, 255),
                        Color.rgb(245, 239, 255)
                }
        );

        root.setBackground(background);

        // 背景の装飾
        addDecoration(root, "✦", 38,
                Color.rgb(112, 155, 235),
                Gravity.TOP | Gravity.START);

        addDecoration(root, "◇", 40,
                Color.rgb(155, 126, 220),
                Gravity.TOP | Gravity.END);

        addDecoration(root, "✧", 35,
                Color.rgb(94, 174, 235),
                Gravity.CENTER_VERTICAL | Gravity.START);

        addDecoration(root, "◆", 32,
                Color.rgb(151, 132, 225),
                Gravity.BOTTOM | Gravity.END);

        // メインパネル
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);

        panel.setPadding(
                dp(18), dp(15), dp(18), dp(14));

        GradientDrawable panelBackground =
                new GradientDrawable();

        panelBackground.setColor(Color.WHITE);
        panelBackground.setCornerRadius(dp(22));
        panelBackground.setStroke(
                dp(2), Color.rgb(167, 151, 231));

        panel.setBackground(panelBackground);
        panel.setElevation(dp(8));

        FrameLayout.LayoutParams panelParams =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                );

        panelParams.setMargins(
                dp(16), dp(14), dp(16), dp(14));

        root.addView(panel, panelParams);

        // タイトル
        TextView title = new TextView(this);

        title.setText("✦ 遊び方 ✦");
        title.setTextSize(29);
        title.setTextColor(Color.rgb(81, 74, 165));
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, dp(5), 0, dp(2));

        panel.addView(title);

        // サブタイトル
        TextView subtitle = new TextView(this);

        subtitle.setText("ENGLISH PICTURE SHIRITORI");
        subtitle.setTextSize(11);
        subtitle.setTextColor(Color.rgb(77, 139, 211));
        subtitle.setTypeface(null, Typeface.BOLD);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, dp(16));

        panel.addView(subtitle);

        // スクロールできる説明部分
        ScrollView scrollView = new ScrollView(this);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        // ゲームの説明
        addHeading(content, "ENGLISH SHIRITORI", true);

        addBody(content,
                "このゲームは、英単語を使って絵しりとりを楽しむゲームです。前の人が描いた絵を見て、英単語の最後のアルファベットから始まる絵を描いていきます。");

        // STEP 1
        addHeading(content, "01　最初の人が絵を描く", false);

        addBody(content,
                "最初の人は、好きな英単語を1つ決めて、その言葉を表す絵を描きます。");

        // STEP 2
        addHeading(content, "02　次の人に交代する", true);

        addBody(content,
                "描き終わったら、絵の名前を英語で入力します。入力が終わると、次の人に交代します。");

        // STEP 3
        addHeading(content, "03　前の人の絵につなげる", false);

        addBody(content,
                "次の人は、前の人が描いた絵から英単語を考えます。その英単語の最後のアルファベットから始まる言葉を考えて、新しい絵を描きます。");

        // STEP 4
        addHeading(content, "04　例えばこんな感じ！", true);

        TextView example = new TextView(this);

        example.setText(
                "APPLE → ELEPHANT → TIGER → RABBIT");

        example.setTextSize(15);
        example.setTextColor(Color.rgb(78, 83, 166));
        example.setTypeface(null, Typeface.BOLD);
        example.setGravity(Gravity.CENTER);
        example.setPadding(
                dp(5), dp(15), dp(5), dp(15));

        GradientDrawable exampleBackground =
                new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{
                                Color.rgb(221, 241, 255),
                                Color.rgb(237, 229, 255)
                        }
                );

        exampleBackground.setCornerRadius(dp(14));
        exampleBackground.setStroke(
                dp(1), Color.rgb(177, 166, 235));

        example.setBackground(exampleBackground);

        LinearLayout.LayoutParams exampleParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        exampleParams.setMargins(
                0, dp(5), 0, dp(8));

        content.addView(example, exampleParams);

        addBody(content,
                "APPLEの最後は「E」なので、次はEから始まるELEPHANT。ELEPHANTの最後は「T」なので、次はTから始まるTIGERを描きます。");

        // STEP 5
        addHeading(content, "05　ゲームの注意点", false);

        addBody(content,
                "本当のしりとりとは違い、単語の最後に「N」が付いても負けにはなりません。Nから始まる英単語を考えて、次につなげましょう。");

        // 最後のメッセージ
        TextView message = new TextView(this);

        message.setText(
                "みんなで協力して、最後まで絵しりとりを楽しもう！");

        message.setTextSize(16);
        message.setTextColor(Color.rgb(104, 91, 190));
        message.setTypeface(null, Typeface.BOLD);
        message.setGravity(Gravity.CENTER);

        message.setPadding(
                dp(5), dp(18), dp(5), dp(18));

        content.addView(message);

        scrollView.addView(content);

        LinearLayout.LayoutParams scrollParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );

        panel.addView(scrollView, scrollParams);

        // 戻るボタン
        Button backButton = new Button(this);

        backButton.setText("戻る");
        backButton.setTextSize(17);
        backButton.setTextColor(Color.WHITE);
        backButton.setTypeface(null, Typeface.BOLD);
        backButton.setAllCaps(false);

        GradientDrawable buttonBackground =
                new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{
                                Color.rgb(67, 139, 226),
                                Color.rgb(132, 104, 210)
                        }
                );

        buttonBackground.setCornerRadius(dp(18));

        backButton.setBackground(buttonBackground);
        backButton.setElevation(dp(5));

        backButton.setOnClickListener(v -> finish());

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(52)
                );

        buttonParams.setMargins(
                0, dp(12), 0, 0);

        panel.addView(backButton, buttonParams);

        setContentView(root);
    }

    // 背景の装飾
    private void addDecoration(
            FrameLayout root,
            String symbol,
            int size,
            int color,
            int gravity) {

        TextView decoration = new TextView(this);

        decoration.setText(symbol);
        decoration.setTextSize(size);
        decoration.setTextColor(color);
        decoration.setGravity(Gravity.CENTER);
        decoration.setAlpha(0.55f);

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        dp(55), dp(55), gravity);

        params.setMargins(
                dp(4), dp(20), dp(4), dp(20));

        root.addView(decoration, params);
    }

    // 見出し
    private void addHeading(
            LinearLayout layout,
            String text,
            boolean blue) {

        TextView heading = new TextView(this);

        heading.setText(text);
        heading.setTextSize(17);
        heading.setTextColor(Color.WHITE);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setPadding(
                dp(12), dp(11), dp(12), dp(11));

        int colorStart;
        int colorEnd;

        if (blue) {
            colorStart = Color.rgb(65, 143, 231);
            colorEnd = Color.rgb(83, 119, 217);
        } else {
            colorStart = Color.rgb(132, 111, 215);
            colorEnd = Color.rgb(157, 129, 222);
        }

        GradientDrawable headingBackground =
                new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{colorStart, colorEnd}
                );

        headingBackground.setCornerRadius(dp(12));
        heading.setBackground(headingBackground);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, dp(12), 0, dp(5));

        layout.addView(heading, params);
    }

    // 説明文
    private void addBody(
            LinearLayout layout,
            String text) {

        TextView body = new TextView(this);

        body.setText(text);
        body.setTextSize(16);
        body.setTextColor(Color.rgb(44, 59, 87));
        body.setLineSpacing(dp(4), 1.0f);
        body.setPadding(
                dp(5), dp(7), dp(5), dp(10));

        layout.addView(body);
    }
}
