package kafka.test;

import com.google.gson.Gson;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Map;

public class TestHelldiversStream {
    public void main(String ...args){
        // decleare topology
        StreamsBuilder builder = new StreamsBuilder();
        Gson gson = new Gson();
        builder.stream("streams-generic-input")
                .to("streams-generic-output");

        // get a source stream
        KStream<String, String> source = builder.stream("helldivers-status");
        KStream<String, Map> mapped = source.mapValues(value -> gson.fromJson(value, Map.class));

        String[] metricArray = {"campaign_status","defend_event","attack_events","statistics"};

        for (String metric:metricArray) {
            //todo don't forget to serialize to JSON
            mapped.mapValues(val -> val.get(metric)).filterNot((key, value) -> value==null ).to("helldivers-"+metric);
        }

    }

}
