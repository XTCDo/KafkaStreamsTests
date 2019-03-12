package kafka.producers;


import com.google.gson.Gson;
import helldivers.Status;
import kafka.generic.producers.GenericThreadedProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.record.Record;
import util.Logging;

import java.util.logging.Level;

public class HelldiversDataProducer extends GenericThreadedProducer<String, String> {
    private final String TAG = "HelldiversDataProducer";
    public HelldiversDataProducer(){
        super("helldivers-status", "localhost:9092");
    }

    public void run(){
        Thread producerThread = new Thread(() -> {
            Logging.log(Level.INFO, "starting producer on topic: "+getTopic(),TAG);
            Status status = new Status();
            Gson gson = new Gson();
            while(true){
                try{
                    // fetch info
                    status.refresh();
                    getProducer().send(new ProducerRecord<String, String>(getTopic(),gson.toJson(status)));

                    Thread.sleep(1000*60); // sleep for one minute
                } catch (Exception e){
                    Logging.error(e, TAG);
                }
            }
        });

        super.run(producerThread);
    }
}
