package kafka.generic.streams;

import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class GenericStream<K, V> {
    private Properties properties;
    private List<String> inputTopics;
    private List<String> outputTopics;

    public GenericStream(String applicationId, String bootStrapServer,
                         Class<? extends Serde> keySerdeClass, Class<? extends Serde> valueSerdeClass){
        properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, keySerdeClass);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, valueSerdeClass);

        final StreamsBuilder builder = new StreamsBuilder();

        builder.stream("streams-plaintext-input").to("streams-pipe-output");
    }



    public static void main(String[] args) throws Exception {
        // Start of by defining the properties for the Stream
        Properties props = new Properties();
        // Name the Streams application
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        // Point it towards the correct kafka broker
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Define in what way the Key of each record should be (de)serialized
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        // Define in what way the Value of each record should be (de)serialized
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Create a StreamsBuilder
        final StreamsBuilder builder = new StreamsBuilder();

        // Stream records in the topic 'kafka.streams-plaintext-input' to the topic
        //  'kafka.streams-pipe-output'
        builder.stream("streams-plaintext-input").to("streams-pipe-output");

        // Create the Topology defined above
        final Topology topology = builder.build();
        System.out.println(topology.describe());

        // Make an actual stream out of the defined topology
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            // Start the kafka.streams application and stop it on ctrl+c
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }

    // invoke without passing an object: works ONLY for static methods
    public static void invoke (Method method, String args)throws InvocationTargetException, IllegalAccessException{
        invoke(null, method, args);
    }
    public static void invoke(Object object, Method method, String arg) throws InvocationTargetException, IllegalAccessException {
        method.invoke(object, arg);
    }


}
