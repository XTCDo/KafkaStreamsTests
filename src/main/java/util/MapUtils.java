package util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapUtils {

    private static String snakeCaseToCamelCase(String snake) {
        Pattern pattern = Pattern.compile("_([a-zA-z])");
        Matcher matcher = pattern.matcher(snake);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static Object safeGet(Map map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return map.get(snakeCaseToCamelCase(key));
        }
    }
}
