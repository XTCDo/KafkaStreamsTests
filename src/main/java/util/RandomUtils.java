package util;

import java.util.Random;

public class RandomUtils {
    public static int randomInteger(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static float randomFloat(float min, float max){
        Random rand = new Random();
        return min + rand.nextFloat()*(max - min);
    }

    public static String randomString(String[] words, int wordLength){
        String [] wordsArray = new String[wordLength];

        for(int i = 0; i < wordLength; i++){
            wordsArray[i] = words[randomInteger(0, words.length - 1)];
        }

        return String.join(" ", wordsArray);
    }
}
