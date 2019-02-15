package kafka.producers;

import com.google.gson.Gson;
import helldivers.HelldiversAPIWrapper;
import helldivers.Status;
import kafka.generic.producers.GenericThreadedProducer;
import util.Logging;

public class HelldiversDataProducer extends GenericThreadedProducer<String, String> {
    private final String TAG = "HelldiversDataProducer";
    public HelldiversDataProducer(){
        super("helldivers-data", "localhost:9092");
    }

    public void run(){
        Thread producerThread = new Thread(() -> {
            Status status = new Status();
            Gson gson = new Gson();
            while(true){
                try{

                } catch (Exception e){
                    Logging.error(e, TAG);
                }
            }
        });

        super.run(producerThread);
    }
}
