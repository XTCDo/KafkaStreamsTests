package kafka.test;

import com.google.gson.Gson;
import helldivers.*;
import kafka.generic.streams.GenericStream;
import org.apache.kafka.common.metrics.Stat;
import org.apache.kafka.common.record.Record;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.processor.TopicNameExtractor;
import util.Config;
import util.Logging;

import java.util.*;

public class TestHelldiversStream {
    private static final String TAG = "TestHelldiversStream";
    public static void main(String ...args){
        // declare topology
        StreamsBuilder builder = new StreamsBuilder();
        Gson gson = new Gson();

        // set up topology

        // get source
        KStream<String, String> source = builder.stream("helldivers-status");

        // process source
        KStream<String, Object> tagged = source
            .mapValues(value -> new Status(gson.fromJson(value, Map.class))) // process from string to map to Status object
            // then re-map to their respective variables
            .flatMap((key, status)->{
            List<KeyValue<String, Object>> result = new LinkedList<>();
            result.add(KeyValue.pair("helldivers-campaign_status", status.getCampaignStatuses())); // campaign_status
            result.add(KeyValue.pair("helldivers-attack_events", status.getAttackEvents())); // attack events
            result.add(KeyValue.pair("helldivers-defend_events", status.getDefendEvents())); // defend events
            result.add(KeyValue.pair("helldivers-statistics", status.getStatistics())); // statistics
            return result;
            });

        // send to dynamic topics
        tagged.to((key, val, recordContext) -> key);
        
        final Topology topology = builder.build();
        Logging.log("topology constructed: "+topology.describe(), TAG);
        // create a generic stream with topology
        GenericStream hdStream = new GenericStream("streams-helldivers", Config.getLocalBootstrapServersConfig(),
                Serdes.StringSerde.class,
                Serdes.StringSerde.class, topology);

        hdStream.run();
    }

}
