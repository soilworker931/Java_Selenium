package framework.utils;

import java.util.Random;

public class StringGenerator {
    public static String textGeneration(int textLength) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder generatedChars = new StringBuilder(textLength);
        for (int i = 0; i < textLength; i++) {
            generatedChars.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return String.valueOf(generatedChars);
    }

    public static String integerNumberGeneration(int min, int max) {
        return String.valueOf((Math.random()*((max-min)+1))+min);
    }
}
