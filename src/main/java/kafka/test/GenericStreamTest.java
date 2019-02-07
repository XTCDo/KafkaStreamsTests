package kafka.test;


import kafka.generic.streams.GenericStream;
import kafka.generic.streams.ObjectSerde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import planets.Planet;
import planets.PlanetBuilder;
import util.Logging;

import java.util.logging.Level;

public class GenericStreamTest {
    private static final String TAG = "GenericStreamTest";
    public static void main(String... args){
        try {
            log("commencing test");

            // first steps to setting up a stream is buildin the topic:
            // in this instance a simple pipe

            log("testing ObjectSerde");
            log("ObjectSerde setup...");
            ObjectSerde objectSerde = new ObjectSerde();

            Planet planet = new PlanetBuilder()
                    .setName("Super-Earth")
                    .setCapitol("UN HQ ONE")
                    .setColor("silver")
                    .setDistanceToSun(10f)
                    .setGravity(1f)
                    .setTemperature(300f).build();
            log("created planet:" + planet.toString());
            float pi = (float) Math.PI;

            log("using ObjectSerde to serialize planet into ByteArray");
            byte[] serializedFloat = objectSerde.serializer().serialize("topic",pi);
            byte[] serializedPlanet = objectSerde.serializer().serialize("topic",planet);

            log("de-serializing result");
            Object obj = objectSerde.deserializer().deserialize("topic", serializedPlanet);
            Object pi_obj = objectSerde.deserializer().deserialize("topic", serializedFloat);
            log("de-serialed to:" +obj.getClass().getName()+"\t" + obj.toString());
            log("de-serialed to:" +pi_obj.getClass().getName()+"\t" + pi_obj.toString());

            log("casting to planet");

            Planet deserializedPlanet = (Planet) obj;
            float repi = (float) pi_obj;
            log("planet de-serialized:" + deserializedPlanet.toString());

            log("building simple pipe Stream:");

            log("constructing toplogy with streamsBuilder");
            StreamsBuilder builder = new StreamsBuilder();
            builder.stream("streams-generic-input").to("streams-generic-output");
            final Topology topology = builder.build();

            log("topology constructed: "+topology.describe());

            log("creating generic Stream with constructed topology...");
            GenericStream pipeStream = new GenericStream("streams-pipe", "localhost:9092", topology);
            log("generic stream constructed");

            log("starting generic pipe stream");
            pipeStream.run();
            log("pipeStream successfully started");


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

}
