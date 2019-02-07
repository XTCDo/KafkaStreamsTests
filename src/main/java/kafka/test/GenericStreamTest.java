package kafka.test;


import kafka.generic.streams.GenericStream;
import kafka.streams.DefaultGenericStream;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import sun.rmi.runtime.Log;
import util.Logging;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;

public class GenericStreamTest {
    private static final String TAG = "GenericStreamTest";
    public static void main(String... args){
        try {
            log("commencing test");

            // first steps to setting up a stream is buildin the topic:
            // in this instance a simple pipe

            log("building simple pipe Stream:");

            log("constructing toplogy with streamsBuilder...");
            StreamsBuilder builder = new StreamsBuilder();
            builder.stream("streams-generic-input").to("streams-generic-output");
            final Topology topology = builder.build();

            log("creating generic Stream with constructed topology...");
            GenericStream pipeStream = new GenericStream("streams-pipe", "localhost:9092", topology);
            log("generic stream constructed");

            log("starting generic pipe stream");
            pipeStream.run();
            log("pipeStream successfully started");

            log("test concluded");
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
