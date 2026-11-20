package com.example.helloandroid;

import java.util.ArrayList;
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

        if (previous.isEmpty() || current.isEmpty()) {
            return false;
        }

        // アルファベット以外を除外
        if (!previous.matches("[a-z]+")
                || !current.matches("[a-z]+")) {
            return false;
        }

        char last = previous.charAt(previous.length() - 1);
        char first = current.charAt(0);

        return last == first;
    }

    // 単語リスト全体の成功数
    // 最初のお題も含めて成功数を数える
    public static int countSuccess(
            ArrayList<String> words,
            String initialLetter) {

        int successCount = 0;

        if (words == null || words.isEmpty()
                || initialLetter == null) {
            return 0;
        }

        // 1人目の単語と最初のお題を判定
        if (words.get(0) != null
                && words.get(0).trim()
                .toLowerCase(Locale.ROOT)
                .startsWith(initialLetter.toLowerCase(Locale.ROOT))) {

            successCount++;
        }

        // 2人目以降のしりとりを判定
        for (int i = 1; i < words.size(); i++) {

            if (checkWord(words.get(i - 1), words.get(i))) {
                successCount++;
            }
        }

        return successCount;
    }
}
