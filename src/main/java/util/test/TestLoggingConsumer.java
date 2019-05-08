package util.test;

import kafka.generic.consumers.GenericThreadedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Logging;

import java.time.Duration;
import java.util.logging.Level;

public class TestLoggingConsumer extends GenericThreadedConsumer<String, String> {
    public TestLoggingConsumer(){
        super("streams-loggingtest-input", "localhost:9092", "TestLoggingConsumer");
    }

    public void run(){
        final String TAG = "TestLoggingConsumer.run";
        Thread consumerThread = new Thread(() -> {
            try {
                while(true) {
                    ConsumerRecords<String, String> records = getConsumer().poll(Duration.ofMillis(10));
                    records.forEach((record) -> {
                        String word = record.value();
                        Logging.log(Level.INFO, word, TAG);
                    });
                }
            } catch (Exception e){
                Logging.log(Level.WARNING, e.getMessage());
            }
        });
        super.run(consumerThread);
    }
}
