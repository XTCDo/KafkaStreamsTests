package kafka.generic.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import util.Logging;

import java.util.Map;
import java.util.logging.Level;

public class ObjectSerializer implements Serializer {
    private static final String TAG = "Serializer";
    /**
     * does nothing
     * @param map
     * @param b
     */
    @Override
    public void configure(Map map, boolean b) {
        //todo figure out what to do here
    }

    /**
     * The core of this class: takes java object as input, converts to a byte array of serialized data
     * @param topic topic associated with data
     * @param object input java object
     * @return serialized data representation of input object
     */
    @Override
    public byte[] serialize(String topic, Object object) {
        Logging.log(Level.INFO,"received: "+ object.toString(), TAG);
        ObjectMapper mapper = new ObjectMapper(); // this will write POJO to JSON
        byte[] ObjectAsByteArray = null;
        try {
            ObjectAsByteArray = mapper.writeValueAsString(object).getBytes();   // convert JSON to byteArray
            Logging.log(Level.INFO,"serialized to: "+ ObjectAsByteArray, TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ObjectAsByteArray;
    }

    /**
     * does nothing temporarily
     */
    @Override
    public void close() {

    }
}
