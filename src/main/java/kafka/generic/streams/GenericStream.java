package kafka.generic.streams;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

public class GenericStream<K, V> {
    // everything kafka related needs this
    private Properties properties;
    private KafkaStreams streams;


    // Class constructor
    public GenericStream(String applicationId, String bootStrapServer,
                         Class<? extends Serde> keySerdeClass, Class<? extends Serde> valueSerdeClass,
                         StreamsBuilder builder){

        // first step: define properties
        properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, keySerdeClass);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, valueSerdeClass);

        // we already have a builder provided HAHA
        final Topology topology = builder.build();

        System.out.println(topology.describe());

        // Make an actual stream out of the defined topology
        streams = new KafkaStreams(topology, properties);
    }

    // constructor with sensible default
    public GenericStream(String appId,String bootStrapServer, StreamsBuilder builder){
        this(appId, bootStrapServer, Serdes.String().getClass(), Serdes.String().getClass(), builder);
    }

    public void run(){
        streams.start();
    }
}
