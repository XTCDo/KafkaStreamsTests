package kafka.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestReplacer {

    public static void main(String[] args) {
        String snek = "this_is_a_snake";
        System.out.println(snakeCaseToCamelCase(snek));
    }

    public static String snakeCaseToCamelCase(String snake){
        Pattern pattern = Pattern.compile("_([a-zA-z])");
        Matcher matcher = pattern.matcher(snake);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

}
