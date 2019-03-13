package kafka.test;

import com.google.gson.Gson;
import helldivers.Status;
import kafka.generic.streams.GenericStream;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.RecordContext;
import org.apache.kafka.streams.processor.TopicNameExtractor;
import util.Config;
import util.Logging;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestHelldiversStream {
    private static final String TAG = "TestHelldiversStream";
    public static void main(String ...args){

        // declare topology
        StreamsBuilder builder = new StreamsBuilder();
        Gson gson = new Gson();

        // set up topology
        Logging.debug("declaring topology",TAG);

        // get source
        Logging.debug("fetching source",TAG);
        KStream<String, String> source = builder.stream("helldivers-status");

        Logging.debug("declaring processors",TAG);

        // process source
        KStream<String, Object> tagged = source
            .mapValues(value -> gson.fromJson(value, Status.class))// process string to Status object
            .flatMap((key, status)->{
            List<KeyValue<String, Object>> result = new LinkedList<>();
            result.add(KeyValue.pair("helldivers-campaign_status", status.getCampaignStatuses())); // campaign_status
            result.add(KeyValue.pair("helldivers-attack_events", status.getAttackEvents())); // attack events
            result.add(KeyValue.pair("helldivers-defend_events", status.getDefendEvents())); // defend events
            result.add(KeyValue.pair("helldivers-statistics", status.getStatistics())); // statistics
            return result;
            });

        Logging.debug("routing to sinks", TAG);

        TopicNameExtractor<String, String> keyFetcher = (key, value, recordContext) -> key;

        // send to dynamic topics
        tagged
                .filterNot((key, object)-> object == null)
                .mapValues(object-> gson.toJson(object))
                .to(keyFetcher);

        final Topology topology = builder.build();
        Logging.log("topology constructed: "+topology.describe(), TAG);
        // create a generic stream with topology
        GenericStream hdStream = new GenericStream("streams-helldivers", Config.getLocalBootstrapServersConfig(),
                Serdes.StringSerde.class,
                Serdes.StringSerde.class, topology);

        hdStream.run();
    }

}


