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

        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        Thread producerThread = new Thread(){
            public void run(){
                try {
                    final String[] WORDS = {
                        "test", "random", "word", "java", "kafka", "apache"
                    };
                    final int MIN_WORDS = 1;
                    final int MAX_WORDS = WORDS.length;

                    while(true){
                        String generatedWord = "";
                        for(int i = MIN_WORDS; i < MAX_WORDS - 1; i++){
                            generatedWord += " ";
                            generatedWord += WORDS[randomInteger(0, WORDS.length - 1)];
                        }
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
