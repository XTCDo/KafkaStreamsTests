package kafka.streams;

import kafka.generic.streams.GenericStream;
import org.apache.kafka.streams.StreamsBuilder;

public class DefaultGenericStream extends GenericStream {

    public DefaultGenericStream(String appId, String bootStrapServer){
        super(appId, bootStrapServer);
    }

    @Override
    public void builderSetup(StreamsBuilder builder) {
        builder.stream("streams-plaintext-input").to("streams-pipe-output");
    }
}
