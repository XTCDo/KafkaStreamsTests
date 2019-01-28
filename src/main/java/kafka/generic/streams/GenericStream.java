package kafka.generic.streams;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class GenericStream<K, V> {
    private Properties properties;
    public GenericStream(String applicationId, String bootStrapServer,
                         Class<? extends Serde> keySerdeClass, Class<? extends Serde> valueSerdeClass){
        properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, keySerdeClass);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, valueSerdeClass);

    }

}
