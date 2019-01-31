package kafka.generic.streams;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;

import java.util.Properties;

public abstract class GenericStream<K, V> {
    // everything kafka related needs this
    private Properties properties;
    private KafkaStreams streams;

    // publically available constructors
    public GenericStream(String applicationId, String bootStrapServer,
                         Class<? extends Serde> keySerdeClass, Class<? extends Serde> valueSerdeClass){
        this(applicationId, bootStrapServer, keySerdeClass, valueSerdeClass, new StreamsBuilder());
    }

    // public constructor with sensible default
    public GenericStream(String appId,String bootStrapServer){
        this(appId, bootStrapServer, new StreamsBuilder());
    }

    // private because only this class is allowed to run these constructors
    // Class constructor
    private GenericStream(String applicationId, String bootStrapServer,
                         Class<? extends Serde> keySerdeClass, Class<? extends Serde> valueSerdeClass,
                         StreamsBuilder builder){

        // first step: define properties
        properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, keySerdeClass);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, valueSerdeClass);

        // we already have a builder provided HAHA
        builderSetup(builder);
        final Topology topology = builder.build();

        System.out.println(topology.describe());

        // Make an actual stream out of the defined topology
        streams = new KafkaStreams(topology, properties);
    }


    private GenericStream(String appId,String bootStrapServer, StreamsBuilder builder){
        this(appId, bootStrapServer, Serdes.String().getClass(), Serdes.String().getClass(), builder);
    }


    /**
     * this method declares what the builder will do:
     * what topics it will listen to, and output, and what happens in between (preferrably lambda functions)
     * @param builder
     */
    public abstract void builderSetup(StreamsBuilder builder);

    public void run(){
        try{
            streams.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        System.out.println("Keyboard interrupt, shutting down thread");
        streams.close();
    }

}
