package kafka.generic.streams;

import org.apache.kafka.common.serialization.Deserializer;
import util.Logging;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;

public class ObjectDeserializer implements Deserializer {
    private static final String TAG = "Deserializer";

    /**
     * configure deserializer, does nothing for the moment
     * @param configs
     * @param isKey
     */
    @Override
    public void configure(Map configs, boolean isKey) { } // todo figure out this method

    /**
     * The core of this class: takes byte array input, converts to a java object
     * @param topic topic associated with the data
     * @param serializedData serialized input data
     * @return java object representation of input data, will return LinkedHashMap in case of complex objects
     */
    @Override
    public Object deserialize(String topic, byte[] serializedData) {
        // input may be null, recommended to catch early
        if (serializedData == null){
            return null;
        }

        Logging.log(Level.FINER,"received: "+ Arrays.toString(serializedData), TAG);
        Object obj = null;

        // try-with-resources auto-closes the resources we create
        try(    ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedData);
                ObjectInputStream in = new ObjectInputStream(inputStream)){

            obj = in.readObject();

        }catch (Exception e){
            Logging.log(Level.SEVERE, Arrays.toString(e.getStackTrace()),TAG);
        }

        Logging.log(Level.FINER,"parsed to: "+ (obj != null ? obj.toString() : "null Object"), TAG);
        return obj;
    }


    /**
     * close for some reason, code will temporarily do nothing
      */
    @Override
    public void close() {
    }
}
