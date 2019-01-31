package kafka.streams;

import kafka.generic.streams.GenericStream;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;


// example class that utilises generic Streams
public class ReverseRecordGeneric extends GenericStream {
    public ReverseRecordGeneric(String appId, String bootStrapServer){
        super(appId, bootStrapServer);
    }

    @Override
    public void builderSetup(StreamsBuilder builder){
        KStream<String, String> source = builder.stream("streams-plaintext-input");
        source.mapValues(
                value -> new StringBuilder(value).reverse().toString())
                .to("streams-reverse-generic-output");
    }
}
