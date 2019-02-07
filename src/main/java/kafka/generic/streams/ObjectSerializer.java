package kafka.generic.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;

public class ObjectSerializer implements Serializer {

    @Override
    public void configure(Map map, boolean b) {
        //todo figure out what to do here
    }

    @Override
    public byte[] serialize(String topic, Object obj) {
        ObjectMapper mapper = new ObjectMapper(); // this will write POJO to JSON
        byte[] ObjectAsByteArray = null;
        try {
            ObjectAsByteArray = mapper.writeValueAsString(obj).getBytes();   // convert JSON to byteArray
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ObjectAsByteArray;
    }

    @Override
    public void close() {

    }
}
