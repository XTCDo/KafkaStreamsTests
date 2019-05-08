package util;

import java.util.Random;

public class RandomUtils {
    /**
     * Returns a random int between min and max (inclusive)
     * @param min minimum value for random int
     * @param max maximum value for random int
     * @return a random int between min and max
     */
    public static int randomInteger(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Returns a random float between min and max
     * @param min minimum value for random float
     * @param max maximum value for random float
     * @retur random float between min and max
     */
    public static float randomFloat(float min, float max) {
        Random rand = new Random();
        return min + rand.nextFloat()*(max - min);
    }

    /**
     * Returns a random word composed of substrings in an array
     * @param words array with substrings that will compose the random word
     * @param wordLength the amount of substrings in the random word
     * @return the random word
     */
    public static String randomString(String[] words, int wordLength) {
        String [] wordsArray = new String[wordLength];

        for(int i = 0; i < wordLength; i++) {
            wordsArray[i] = words[randomInteger(0, words.length - 1)];
        }

        return String.join(" ", wordsArray);
    }
}
