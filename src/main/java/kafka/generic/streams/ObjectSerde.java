package kafka.generic.streams;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.security.PublicKey;
import java.util.Map;

public class ObjectSerde implements Serde {
    private ObjectSerializer serializer;
    private ObjectDeserializer deserializer;

    public ObjectSerde(){
        this.serializer = new ObjectSerializer();
        this.deserializer = new ObjectDeserializer();
    }

    @Override
    public void configure(Map map ,boolean isKey) { } // todo figure out

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer serializer() {
        return serializer;
    }

    @Override
    public Deserializer deserializer() {
        return deserializer;
    }

}
