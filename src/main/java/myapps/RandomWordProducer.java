package myapps;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class RandomWordProducer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        // Create producer with key and value both being String
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        // Create a thread to keep our producer going
        Thread producerThread = new Thread(){
            public void run(){
                try {
                    // List of random words
                    final String[] WORDS = {
                        "test", "random", "word", "java", "kafka", "apache"
                    };
                    final int WORD_LENGTH = 4;

                    while(true){
                        // Add random words to generatedWord
                        String[] generatedWordArray = new String[WORD_LENGTH];
                        for(int i = 0; i < WORD_LENGTH; i++){
                            generatedWordArray[i] = WORDS[randomInteger(0, WORDS.length - 1)];
                        }

                        String generatedWord = String.join(" ", generatedWordArray);

                        // Send the random word to the topic
                        producer.send(new ProducerRecord<String, String>("streams-plaintext-input",
                                generatedWord, generatedWord));
                        Thread.sleep(1000);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        final CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook"){
            @Override
            public void run(){
                producer.close();
                latch.countDown();
            }
        });

        try {
            producerThread.start();
            latch.await();
        } catch (Throwable e){
            System.exit(1);
        }

        System.exit(0);
    }

	private static int randomInteger(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
}
