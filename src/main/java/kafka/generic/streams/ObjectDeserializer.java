package kafka.generic.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import util.Logging;

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
        Logging.log(Level.INFO,"received: "+ serializedData, TAG);
        // input may be null, recommended to catch early
        if (serializedData == null){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper(); // this will read JSON as POJO
        Object obj = null;
        try {
            obj = mapper.readValue(serializedData, Object.class); // parse input to generic object
            Logging.log(Level.INFO,"parsed to: "+ obj.toString(), TAG);
        }
        catch (Exception e) {
            Logging.log(Level.SEVERE,e.toString(), TAG);
        }

        return obj;
    }


    /**
     * close for some reason, code will temporarily do nothing
      */
    @Override
    public void close() {
    }
}
