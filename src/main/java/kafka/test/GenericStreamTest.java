package kafka.test;


import kafka.generic.streams.GenericStream;
import org.apache.kafka.streams.StreamsBuilder;

public class GenericStreamTest {

    public static void main(String... args){

        // Create a StreamsBuilder
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream("streams-plaintext-input").to("streams-pipe-output");

        // now use that builder to make a generic Stream
        GenericStream pipeStream = new GenericStream<String, String>("streams-generic-pipe","localhost:9092",builder);
        pipeStream.run();
    }
}
