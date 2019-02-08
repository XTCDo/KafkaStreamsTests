package kafka.test;


import kafka.generic.streams.GenericStream;
import kafka.generic.streams.ObjectSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import planets.PlanetBuilder;
import util.Logging;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class GenericStreamTest {
    private static final String TAG = "GenericStreamTest";
    public static void main(String... args){
        try {
            log("commencing test\n\n");
            simpleSerdeTest();
            serdeTest();

            //pipeTest();
            // first steps to setting up a stream is buildin the topic:
            // in this instance a simple pipe

            log("testing concluded");
        }
        catch (Throwable e){
            error(e);
        }
    }

    public static void log(String message){
        Logging.log(Level.INFO, message, TAG);
    }

    public static void error(Throwable err){
        Logging.log(Level.INFO, err.toString(), TAG);
    }

    public static void simpleSerdeTest(){
        log("initiating simple Serde test:\n");

        ObjectSerde objectSerde = new ObjectSerde();
        testObject(objectSerde, 42);
        testObject(objectSerde, -200);
        testObject(objectSerde, -200);
        testObject(objectSerde, "strings");

    }

    public static void testObject(ObjectSerde serde, Object testObject){
        log("testing object:" +testObject.toString());
        try {
            byte[] serialized = serde.serializer().serialize("topic", 123f);
            log("serialized to:" + Arrays.toString(serialized));

            Object returnObject = serde.deserializer().deserialize("topic", serialized);
            log("de-serialized back into:" + returnObject.toString());
        }catch (Exception e){
            error(e);
        }
    }

    public static void serdeTest(){
        log("initiating Serde test:\n");

        log("ObjectSerde setup");
        ObjectSerde objectSerde = new ObjectSerde();

        log("generating objects");

        List<Object> objects= new ArrayList<>();
        objects.add("lorem ipsum dolor amet...");
        objects.add(123);
        objects.add(-200);
        objects.add(42f);
        objects.add(true);
        objects.add('b');
        objects.add(0xCCFF33);
        objects.add(new PlanetBuilder()
                .setName("Super-Earth")
                .setCapitol("UN HQ ONE")
                .setColor("silver")
                .setDistanceToSun(10f)
                .setGravity(1f)
                .setTemperature(300f).build());
        log("created list of various objects of types: ["+objects.stream().map(obj -> obj.getClass().getName()).collect(Collectors.joining(" | "))+"]");


        log("using ObjectSerde to serialize objects into ByteArray");
        List<byte[]> serializedObjects = objects.stream()
                .map(object-> objectSerde.serializer().serialize("topic",object))
                .collect(Collectors.toList());

        log("using ObjectSerde to de-serialize results back into Objects");
        List<Object> deserializedObjects = serializedObjects.stream()
                .map(serializedObject-> objectSerde.deserializer().deserialize("topic", serializedObject))
                .collect(Collectors.toList());

        log("deserialized into list of various objects of types: ["+deserializedObjects.stream()
                .map(obj -> obj.getClass().getName())
                .collect(Collectors.joining(" | ")) + "]");

        log("result: ["+deserializedObjects.stream().map(Object::toString)
                .collect(Collectors.joining(" | ")) + "]");

        log("Serde test concluded\n");
    }

    public static void pipeTest(){
        log("Initiating generic streams test\n");

        log("constructing pipe topology with streamsBuilder");

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream("streams-generic-input").to("streams-generic-output");
        final Topology topology = builder.build();

        log("topology constructed: "+topology.describe());

        log("creating generic Stream with constructed topology...");
        GenericStream pipeStream = new GenericStream("streams-pipe", "localhost:9092", topology);
        log("generic stream constructed");

        log("starting generic pipe stream");

        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                try {
                    pipeStream.run();
                } catch (Throwable e) {
                    error(e);
                }
            }
        });

        try {
            // Start the kafka.streams application and stop it on ctrl+c
            pipeStream.run();
            log("pipeStream successfully started\n");
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }

        System.exit(0);
    }

}