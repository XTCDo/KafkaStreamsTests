package kafka.generic.streams;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

    // for invoking non-static methods, we require an object to be passed
    public static Object invoke (Object obj, Method method){
        return null;
    }

    public static Object invoke (Object obj, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        Type returnType = method.getReturnType();
        if (returnType.equals(Void.TYPE)){
            method.invoke(obj, args);
            return null;
        }

        return method.invoke(obj, args);
    }



    // for invoking static methods
    public static Object staticinvoke (Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        if (method.getReturnType().equals(Void.TYPE)){
            method.invoke(null, args);
            return null;
        }
        return method.invoke(null, args);
    }

}
