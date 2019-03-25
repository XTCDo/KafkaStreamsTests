package kafka.generic.streams;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import util.Logging;

import java.util.Properties;
import java.util.logging.Level;


public class GenericStream {
    private static final String TAG = "GenericStream";
    // everything kafka related needs this
    private String applicationId;
    private Properties properties;
    private KafkaStreams streams;


    /**
     * generic stream constructor
     * @param applicationId the name of this stream
     * @param bootStrapServer location of broker server this stream will listen to/ be registered to
     * @param defaultKeySerdeClass default class for key serializer/deserializer
     * @param defaultValueSerdeClass default class for value serializer/deserializer
     * @param topology topology this stream will be built according to: defines input topic, process and output topic.
     */
    public GenericStream(String applicationId, String bootStrapServer,
                         Class<? extends Serde> defaultKeySerdeClass, Class<? extends Serde> defaultValueSerdeClass,
                         Topology topology){

        this.applicationId= applicationId;

        // first define properties
        properties = new Properties();

        // name and server address
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);

        // default serializer/deserializers
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, defaultKeySerdeClass);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, defaultValueSerdeClass);

       this.streams = new KafkaStreams(topology, properties);
    }

    /**
     * generic stream constructor with sensible defaults
     * @param appId the name of this stream
     * @param bootStrapServer location of broker server this stream will listen to/ be registered to
     * @param topology topology this stream will be built according to, defines: input topics, processes and output topics
     */
     public GenericStream(String appId, String bootStrapServer, Topology topology){
        this(appId, bootStrapServer, Serdes.String().getClass(), ObjectSerde.class, topology);
    }


    // function calls

    /**
     * start the streams defined in this objects constructor
     */
    public void run(){
        try{
            Logging.log(Level.INFO,"starting stream: "+applicationId,TAG);
            streams.start();
        }catch (Exception e){
            Logging.error(e, TAG);
        }
    }

    /**
     * gracefully terminating streams
     */
    public void close(){
        Logging.log(Level.INFO,"terminating stream: "+applicationId,TAG);
        streams.close();
    }

}