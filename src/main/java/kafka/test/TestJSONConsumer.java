package kafka.test;

import kafka.generic.consumers.GenericJSONConsumer;
import util.Config;

public class TestJSONConsumer {
    public static void main(String ...args){
        new GenericJSONConsumer(Config.getLocalInfluxUrl(),"test-json-input", Config.getLocalBootstrapServersConfig(), "generic-json-consumer-test",
                "Testing","generic-json-test");
    }
}
