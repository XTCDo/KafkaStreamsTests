package util;

public class Config {
    public static String getLocalBootstrapServersConfig(){
        return "localhost:9092";
    }

    public static String getRemoteBootstrapServersConfig(){
        return "apachekafka-a6909623a228.victhorious.com:9091";
    }
}
