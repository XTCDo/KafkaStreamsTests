package kafka.streams;

import kafka.generic.streams.GenericStream;
import org.apache.kafka.streams.StreamsBuilder;

import java.util.Map;

public class GenericPipe extends GenericStream {

    public GenericPipe(String appId, String bootStrapServer) {
        super(appId, bootStrapServer);
    }

    public GenericPipe(String appId, String bootStrapServer, String inputTopic, String outputTopic) {
        super(appId, bootStrapServer);
    }

    @Override
    public void builderSetup(StreamsBuilder builder, Map<String, String> topicMap) {

    }

    public StreamsBuilder pipe(StreamsBuilder builder, String inputTopic, String outputTopic){
        builder.stream(inputTopic).to(outputTopic);
        return builder;
    }

}
