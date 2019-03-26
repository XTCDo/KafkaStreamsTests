package kafka.producers;


import com.google.gson.Gson;
import helldivers.Status;
import kafka.generic.producers.GenericThreadedProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import util.Logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class HelldiversDataProducer extends GenericThreadedProducer<String, String> {

    private final String TAG = "HelldiversDataProducer";

    public HelldiversDataProducer() {
        super("helldivers-status", "localhost:9092");
    }

    public void run() {
        Thread producerThread = new Thread(() -> {
            Logging.log(Level.INFO, "starting producer on topic: " + getTopic(), TAG);
            Status status = new Status();
            Gson gson = new Gson();
            while (true) {
                try {
                    // fetch info
                    status.refresh();
                    Logging
                        .log("sending data at " + new SimpleDateFormat("hh:mm:SS")
                            .format(new Date()), TAG);

                    getProducer()
                        .send(new ProducerRecord<String, String>(getTopic(), gson.toJson(status)));

                    Thread.sleep(1000 * 60); // sleep for one minute
                } catch (Exception e) {
                    Logging.error(e, TAG);
                }
            }
        });

        super.run(producerThread);
    }
}
