package kafka.test;

import com.google.gson.Gson;
import kafka.generic.streams.GenericStream;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import util.Config;
import util.Logging;

import java.util.Map;

public class TestHelldiversStream {
    private static final String TAG = "TestHelldiversStream";
    public void main(String ...args){
        // decleare topology
        StreamsBuilder builder = new StreamsBuilder();
        Gson gson = new Gson();


        // set up topology
        KStream<String, String> source = builder.stream("helldivers-status");
        KStream<String, Map> mapped = source.mapValues(value -> gson.fromJson(value, Map.class));

        String[] metricArray = {"campaign_status","defend_event","attack_events","statistics"};

        for (String metric:metricArray) {
            //todo don't forget to serialize to JSON
            mapped
                    .mapValues(val -> val.get(metric)) // get the specified metric
                    .filterNot((key, value) -> value==null ) // filter out null-variables
                    .mapValues(val -> gson.toJson(val)) // map result to JSON
                    .to("helldivers-"+metric); // send to new topics
        }

        final Topology topology = builder.build();
        Logging.log("topology constructed: "+topology.describe(), TAG);
        // create a generic stream with topology
        GenericStream hdStream = new GenericStream("streams-helldivers", Config.getLocalBootstrapServersConfig(),
                Serdes.StringSerde.class,
                Serdes.StringSerde.class, topology);

        hdStream.run();
    }

}
