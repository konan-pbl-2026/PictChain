
package com.example.helloandroid;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HowToPlay extends AppCompatActivity {

    private int dp(int value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面全体
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        layout.setPadding(dp(24), dp(24), dp(24), dp(20));

        // タイトル
        TextView title = new TextView(this);
        title.setText("遊び方");
        title.setTextSize(28);
        title.setTextColor(Color.BLACK);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, dp(10), 0, dp(25));

        layout.addView(title);

        // 説明文をスクロールできるようにする
        ScrollView scrollView = new ScrollView(this);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        // ゲームの説明
        addText(content, "英語の絵しりとり",
                22, true);

        addText(content,
                "このゲームは、英単語を使って絵しりとりを楽しむゲームです。前の人が描いた絵を見て、英単語の最後のアルファベットから始まる絵を描いていきます。",
                17, false);

        // ルール1
        addText(content, "① 最初の人が絵を描く",
                19, true);

        addText(content,
                "最初の人は、好きな英単語を1つ決めて、その言葉を表す絵を描きます。",
                17, false);

        // ルール2
        addText(content, "② 次の人に交代する",
                19, true);

        addText(content,
                "描き終わったら、絵の名前を英語で入力します。入力が終わると、次の人に交代します。",
                17, false);

        // ルール3
        addText(content, "③ 前の人の絵につなげる",
                19, true);

        addText(content,
                "次の人は、前の人が描いた絵から英単語を考えます。その英単語の最後のアルファベットから始まる言葉を考えて、新しい絵を描きます。",
                17, false);

        // 例
        addText(content, "④ 例えばこんな感じ！",
                19, true);

        addText(content,
                "APPLE → ELEPHANT → TIGER → RABBIT",
                18, true);

        addText(content,
                "APPLEの最後は「E」なので、次はEから始まるELEPHANT。ELEPHANTの最後は「T」なので、次はTから始まるTIGERを描きます。",
                17, false);

        // 注意点
        addText(content, "⑤ ゲームの注意点",
                19, true);

        addText(content,
                "本当のしりとりとは違い、単語の最後に「N」が付いても負けにはなりません。Nから始まる英単語を考えて、次につなげましょう。",
                17, false);

        addText(content,
                "このように、順番に絵を描いて英単語をつなげていきます。みんなで協力して最後まで楽しみましょう！",
                17, false);

        scrollView.addView(content);

        LinearLayout.LayoutParams scrollParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );

        layout.addView(scrollView, scrollParams);

        // 戻るボタン
        Button backButton = new Button(this);
        backButton.setText("戻る");
        backButton.setOnClickListener(v -> finish());

        layout.addView(backButton);

        setContentView(layout);
    }

    // 説明文を追加するメソッド
    private void addText(LinearLayout layout,
                         String text,
                         int size,
                         boolean bold) {

        TextView textView = new TextView(this);

        textView.setText(text);
        textView.setTextSize(size);
        textView.setTextColor(Color.BLACK);
        textView.setLineSpacing(dp(4), 1.0f);

        if (bold) {
            textView.setTypeface(null, Typeface.BOLD);
        }

        textView.setPadding(0, dp(8), 0, dp(12));

        layout.addView(textView);
    }
}
