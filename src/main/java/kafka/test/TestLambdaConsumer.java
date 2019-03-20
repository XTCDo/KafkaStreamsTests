package kafka.test;

import kafka.generic.consumers.GenericRunnableConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Config;
import util.Logging;

import java.util.function.Consumer;

public class TestLambdaConsumer {
    public static void main(String ...args){

        Consumer<ConsumerRecords> cons = consumerRecords -> consumerRecords.forEach(record-> Logging.log(record.toString()));

        new GenericRunnableConsumer("test-input", Config.getLocalBootstrapServersConfig(), "generic-runnable-consumer", cons).run();

    }
}

