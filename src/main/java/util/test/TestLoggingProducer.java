package util.test;

import kafka.generic.producers.GenericThreadedProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import util.Logging;
import util.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TestLoggingProducer extends GenericThreadedProducer<String, String> {
    public TestLoggingProducer(){
        super("streams-loggingtest-input", "localhost:9092");
    }

    public void run(){
        final String TAG = "TestLoggingProducer.run";
        Thread testLoggingProducerThread = new Thread(() -> {
            List<Level> levels = new ArrayList<>();
            levels.add(Level.FINEST);
            levels.add(Level.FINER);
            levels.add(Level.FINE);
            levels.add(Level.INFO);
            levels.add(Level.CONFIG);
            levels.add(Level.WARNING);
            levels.add(Level.SEVERE);

            List<String> words = new ArrayList<>();
            words.add("RAMMSTEIN");
            words.add("Alestorm");
            words.add("Sabaton");
            words.add("Billy Talent");
            words.add("Mr.Kitty");
            words.add("August Burns Red");
            words.add("Bring Me The Horizon");

            while(true) {
                try {
                    Level randomLevel = levels.get(RandomUtils.randomInteger(0, levels.size() - 1));
                    String randomWord = words.get(RandomUtils.randomInteger(0, words.size() - 1));
                    getProducer().send(new ProducerRecord(getTopic(), randomLevel.getName(), randomWord));
                    Logging.log(randomLevel, randomWord, TAG);
                    Thread.sleep(100);
                } catch (Exception e){
                    e.printStackTrace();
                    Logging.log(Level.WARNING, e.getMessage());
                }
            }
        });

        super.run(testLoggingProducerThread);
    }
}
