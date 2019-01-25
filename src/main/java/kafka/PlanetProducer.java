package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class PlanetProducer {
    public static void main(String[] args){
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
        Thread producerThread = new Thread(() -> {
            try {
                List<Planet> planets = PlanetProvider.getPlanets();

                while(true) {
                    for(Planet p : planets){
                        producer.send(new ProducerRecord<String, String>("streams-planets-input",
                                p.getName(), p.toString()));
                        Thread.sleep(100);
                    }
                    //Thread.sleep(1000);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        });

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
        } catch(Throwable e){
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

}
