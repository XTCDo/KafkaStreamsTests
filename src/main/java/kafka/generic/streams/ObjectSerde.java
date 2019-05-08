package kafka.generic.streams;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ObjectSerde implements Serde {
    private ObjectSerializer serializer;
    private ObjectDeserializer deserializer;

    /**
     * constructor for this class, creates a serializer and deserializer to use
     */
    public ObjectSerde() {
        this.serializer = new ObjectSerializer();
        this.deserializer = new ObjectDeserializer();
    }

    /**
     * does nothing for the time being
     * @param map
     * @param isKey
     */
    @Override
    public void configure(Map map ,boolean isKey) { } // todo figure out

    /**
     * close child objects when terminating
     */
    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    /**
     * serializer part of this serializer/deserializer
     * @return serializer
     */
    @Override
    public Serializer serializer() {
        return serializer;
    }

    /**
     * de-serializer part of this serializer/deserializer
     * @return deserializer
     */
    @Override
    public Deserializer deserializer() {
        return deserializer;
    }

}
