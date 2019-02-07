package kafka.generic.streams;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.io.Serializable;
import java.util.Properties;


public class GenericStream {
    // everything kafka related needs this
    private Properties properties;
    private KafkaStreams streams; // --> this object is very necessary


    // Class constructor
    public GenericStream(String applicationId, String bootStrapServer,
                         Class<? extends Serde> defaultKeySerdeClass, Class<? extends Serde> defaultValueSerdeClass,
                         Topology topology){

        // first step: define properties
        properties = new Properties();

        // name and server address
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);

        // default serializer/deserializers
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, defaultKeySerdeClass);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, defaultValueSerdeClass);

       this.streams = new KafkaStreams(topology, properties);
    }

     public GenericStream(String appId,String bootStrapServer, Topology topology){
        this(appId, bootStrapServer, Serdes.String().getClass(), ObjectSerde.class, topology);
    }




    // function calls

    /**
     * start the streams this object holds
     */
    public void run(){
        try{
            streams.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * gracefully terminating streams
     */
    public void close(){
        streams.close();
    }
}