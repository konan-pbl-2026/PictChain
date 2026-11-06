
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

        // おもちゃ部屋の壁紙風背景
        GradientDrawable wallpaper = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{
                        Color.rgb(174, 222, 255),
                        Color.rgb(218, 239, 255),
                        Color.rgb(255, 220, 169)
                }
        );

        root.setBackground(wallpaper);

        // 背景のおもちゃ
        addDecoration(root, "🧸", 34,
                Gravity.TOP | Gravity.START, 2, 20);

        addDecoration(root, "⭐", 30,
                Gravity.TOP | Gravity.END, 2, 75);

        addDecoration(root, "🧩", 30,
                Gravity.CENTER_VERTICAL | Gravity.START, 0, 0);

        addDecoration(root, "🚀", 32,
                Gravity.CENTER_VERTICAL | Gravity.END, 0, 0);

        addDecoration(root, "🚗", 32,
                Gravity.BOTTOM | Gravity.START, 2, 25);

        addDecoration(root, "⚽", 34,
                Gravity.BOTTOM | Gravity.END, 2, 30);

        // 説明部分のパネル
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(
                dp(18), dp(16), dp(18), dp(14));

        GradientDrawable panelBackground = new GradientDrawable();
        panelBackground.setColor(Color.rgb(255, 250, 235));
        panelBackground.setCornerRadius(dp(22));
        panelBackground.setStroke(
                dp(3), Color.rgb(145, 102, 202));

        panel.setBackground(panelBackground);
        panel.setElevation(dp(8));

        FrameLayout.LayoutParams panelParams =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                );

        panelParams.setMargins(
                dp(18), dp(12), dp(18), dp(12));

        root.addView(panel, panelParams);

        // タイトル
        TextView title = new TextView(this);
        title.setText("🎮 遊び方 🎨");
        title.setTextSize(28);
        title.setTextColor(Color.rgb(91, 48, 145));
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, dp(5), 0, dp(18));

        panel.addView(title);

        // スクロールできる説明部分
        ScrollView scrollView = new ScrollView(this);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        // ゲームの説明
        addHeading(content, "英語の絵しりとり");

        addBody(content,
                "このゲームは、英単語を使って絵しりとりを楽しむゲームです。前の人が描いた絵を見て、英単語の最後のアルファベットから始まる絵を描いていきます。");

        // STEP 1
        addHeading(content, "① 最初の人が絵を描く");

        addBody(content,
                "最初の人は、好きな英単語を1つ決めて、その言葉を表す絵を描きます。");

        // STEP 2
        addHeading(content, "② 次の人に交代する");

        addBody(content,
                "描き終わったら、絵の名前を英語で入力します。入力が終わると、次の人に交代します。");

        // STEP 3
        addHeading(content, "③ 前の人の絵につなげる");

        addBody(content,
                "次の人は、前の人が描いた絵から英単語を考えます。その英単語の最後のアルファベットから始まる言葉を考えて、新しい絵を描きます。");

        // STEP 4
        addHeading(content, "④ 例えばこんな感じ！");

        TextView example = new TextView(this);
        example.setText("APPLE → ELEPHANT → TIGER → RABBIT");
        example.setTextSize(16);
        example.setTextColor(Color.rgb(75, 48, 115));
        example.setTypeface(null, Typeface.BOLD);
        example.setGravity(Gravity.CENTER);
        example.setPadding(dp(6), dp(12), dp(6), dp(12));

        GradientDrawable exampleBackground = new GradientDrawable();
        exampleBackground.setColor(Color.rgb(255, 229, 151));
        exampleBackground.setCornerRadius(dp(16));
        example.setBackground(exampleBackground);

        LinearLayout.LayoutParams exampleParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        exampleParams.setMargins(0, dp(4), 0, dp(10));
        content.addView(example, exampleParams);

        addBody(content,
                "APPLEの最後は「E」なので、次はEから始まるELEPHANT。ELEPHANTの最後は「T」なので、次はTから始まるTIGERを描きます。");

        // STEP 5
        addHeading(content, "⑤ ゲームの注意点");

        addBody(content,
                "本当のしりとりとは違い、単語の最後に「N」が付いても負けにはなりません。Nから始まる英単語を考えて、次につなげましょう。");

        addBody(content,
                "このように、順番に絵を描いて英単語をつなげていきます。みんなで協力して最後まで楽しみましょう！");

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
        backButton.setTextSize(18);
        backButton.setTextColor(Color.WHITE);
        backButton.setAllCaps(false);

        GradientDrawable buttonBackground = new GradientDrawable();
        buttonBackground.setColor(Color.rgb(126, 76, 185));
        buttonBackground.setCornerRadius(dp(25));

        backButton.setBackground(buttonBackground);
        backButton.setElevation(dp(4));

        backButton.setOnClickListener(v -> finish());

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(52)
                );

        buttonParams.setMargins(0, dp(12), 0, 0);

        panel.addView(backButton, buttonParams);

        setContentView(root);
    }

    // 背景におもちゃを置く
    private void addDecoration(
            FrameLayout root,
            String emoji,
            int size,
            int gravity,
            int horizontalMargin,
            int verticalMargin) {

        TextView decoration = new TextView(this);
        decoration.setText(emoji);
        decoration.setTextSize(size);
        decoration.setGravity(Gravity.CENTER);

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        dp(48), dp(48), gravity);

        params.setMargins(
                dp(horizontalMargin),
                dp(verticalMargin),
                dp(horizontalMargin),
                dp(verticalMargin)
        );

        root.addView(decoration, params);
    }

    // 見出し
    private void addHeading(
            LinearLayout layout,
            String text) {

        TextView heading = new TextView(this);

        heading.setText(text);
        heading.setTextSize(17);
        heading.setTextColor(Color.rgb(85, 48, 132));
        heading.setTypeface(null, Typeface.BOLD);
        heading.setPadding(
                dp(12), dp(10), dp(12), dp(10));

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.rgb(231, 215, 255));
        background.setCornerRadius(dp(18));
        background.setStroke(
                dp(1), Color.rgb(183, 148, 225));

        heading.setBackground(background);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, dp(10), 0, dp(5));

        layout.addView(heading, params);
    }

    // 説明文
    private void addBody(
            LinearLayout layout,
            String text) {

        TextView body = new TextView(this);

        body.setText(text);
        body.setTextSize(16);
        body.setTextColor(Color.rgb(55, 49, 65));
        body.setLineSpacing(dp(4), 1.0f);
        body.setPadding(
                dp(5), dp(7), dp(5), dp(9));

        layout.addView(body);
    }
}
