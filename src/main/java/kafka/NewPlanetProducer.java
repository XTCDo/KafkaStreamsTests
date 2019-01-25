package kafka;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;

public class NewPlanetProducer extends AbstractThreadedProducer {
    public NewPlanetProducer(String topic, String bootStrapServer, Object keySerializerClass, Object valueSerializerClass, String acks, int retries, int batchSize, int lingerMS, int bufferMemory) {
        super(topic, bootStrapServer, keySerializerClass, valueSerializerClass, acks, retries, batchSize, lingerMS, bufferMemory);
    }

    private NewPlanetProducer(String topic, String bootStrapServer){
        super(topic, bootStrapServer);
    }

    public void run(){
        Thread producerThread = new Thread(() -> {
            try {
                List<Planet> planets = PlanetProvider.getPlanets();

                while(true) {
                    for(Planet p : planets){
                        getProducer().send(new ProducerRecord(getTopic(), p.getName(), p.toString()));
                        Thread.sleep(100);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        super.run(producerThread);
    }
}
