package kafka.producers;

import com.google.gson.Gson;
import helldivers.HelldiversAPIWrapper;
import helldivers.Status;
import kafka.generic.producers.GenericThreadedProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import util.Logging;

public class HelldiversDataProducer extends GenericThreadedProducer<String, String> {
    private final String TAG = "HelldiversDataProducer";
    public HelldiversDataProducer(){
        super("helldivers-data-input", "localhost:9092");
    }

    public void run(){
        Thread producerThread = new Thread(() -> {
            while(true){
                try{
                    getProducer().send(new ProducerRecord(getTopic(), HelldiversAPIWrapper.getAPIResponse()));
                    Thread.sleep(1000);
                } catch (Exception e){
                    Logging.error(e, TAG);
                }
            }
        });

        super.run(producerThread);
    }
}
