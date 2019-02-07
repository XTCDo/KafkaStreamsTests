package kafka.generic.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ObjectDeserializer implements Deserializer {
    @Override
    public void configure(Map map, boolean b) { } // todo figure out this method

    @Override
    public Object deserialize(String topic, byte[] serializedObject) {

        ObjectMapper mapper = new ObjectMapper(); // this will read JSON as POJO
        Object obj = null;
        try {
            obj = mapper.readValue(topic, Object.class); // parse input to generic object
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void close() {
    }
}
