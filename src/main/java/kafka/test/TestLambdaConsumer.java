package kafka.test;

import kafka.generic.consumers.GenericRunnableConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import sun.rmi.runtime.Log;
import util.Config;
import util.Logging;

import java.util.function.Consumer;

public class TestLambdaConsumer {
    public static void main(String ...args){
        Logging.debug("setting up java consumer");
        Consumer<ConsumerRecords> cons = consumerRecords -> consumerRecords.forEach(record-> Logging.log(record.toString()));
        Logging.debug("constructing generic runnable consumer");
        GenericRunnableConsumer test = new GenericRunnableConsumer("test-input", Config.getLocalBootstrapServersConfig(), "generic-runnable-consumer", cons);
        Logging.debug("running constructed consumer");
        test.run();
    }
}

