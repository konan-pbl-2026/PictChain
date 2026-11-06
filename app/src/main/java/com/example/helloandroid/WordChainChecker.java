
package com.example.helloandroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WordChainChecker {

    // しりとりの成立判定
    public static boolean checkWord(
            String previousWord,
            String currentWord) {

        if (previousWord == null || currentWord == null) {
            return false;
        }

        String previous = previousWord.trim()
                .toLowerCase(Locale.ROOT);

        String current = currentWord.trim()
                .toLowerCase(Locale.ROOT);

        // 空欄チェック
        if (previous.isEmpty() || current.isEmpty()) {
            return false;
        }

        // アルファベット以外を除外
        if (!previous.matches("[a-z]+")
                || !current.matches("[a-z]+")) {
            return false;
        }

        // nで終わる単語は失敗
        if (previous.endsWith("n")) {
            return false;
        }

        // 最後の文字と最初の文字を比較
        char last = previous.charAt(previous.length() - 1);
        char first = current.charAt(0);

        return last == first;
    }

    // 単語リスト全体の成功数を数える
    public static int countSuccess(
            ArrayList<String> words) {

        int successCount = 0;

        for (int i = 1; i < words.size(); i++) {

            if (checkWord(words.get(i - 1), words.get(i))) {
                successCount++;
            }
        }

        return successCount;
    }
}
