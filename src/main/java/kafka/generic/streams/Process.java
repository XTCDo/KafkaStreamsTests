package kafka.generic.streams;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

import java.util.List;

public class Process {
    private List<String> inputTopics;
    private List<String> outputTopics;
    private StreamsBuilder builder; //incorrect, needs to be improved

    public Process(List<String> inputTopics,List<String> outputTopics, StreamsBuilder builder){
        this.inputTopics = inputTopics;
        this.outputTopics = outputTopics;
        this.builder= builder; // how do I specifically describe a connection between these? --> KStream?
    }

}
