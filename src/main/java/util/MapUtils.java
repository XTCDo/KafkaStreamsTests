package util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapUtils {

    /**
     * Helper function that turns a String in snake_case into a String in camelCase
     *
     * @param snake The String in snake_case
     * @return The String in camelCase
     */
    private static String snakeCaseToCamelCase(String snake) {
        // Regex pattern to match '_x' (with x being any character)
        Pattern pattern = Pattern.compile("_([a-zA-z])");
        Matcher matcher = pattern.matcher(snake);
        StringBuffer stringBuffer = new StringBuffer();

        // Replace all instances of '_x' with X
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }

    /**
     * Gets a key from a provided map safely when the key can either be snake_case or camelCase
     *
     * @param map The Map to get the key from
     * @param key The key that can be either snake_case or camelCase
     * @return The value related to the key
     */
    public static Object safeGet(Map map, String key) {
        // Check if the map contains the key
        // if it doesn't then the key is probably in camelCase
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return map.get(snakeCaseToCamelCase(key));
        }
    }
}
