package com.example.helloandroid;
import java.util.Random;

public class InitialLetter {

    // 絵にしやすいアルファベット
    private static final String[] LETTERS = {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "R", "S", "T", "W"
    };

    // ランダムにお題を選ぶ
    public static String getRandomLetter() {
        Random random = new Random();

        return LETTERS[random.nextInt(LETTERS.length)];
    }
}