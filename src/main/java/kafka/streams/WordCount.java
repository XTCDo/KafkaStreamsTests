package kafka.streams;


import kafka.generic.streams.GenericStream;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import util.Config;
import util.Logging;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class WordCount {
    private static final String TAG = "WordCount";

    public static void main(String[] args) throws Exception {

        final StreamsBuilder builder = new StreamsBuilder();

        // get a source topic
        KStream<String, String> source = builder.stream("streams-plaintext-input");

        // an example that also shows the possibility of defining Ser/Des at topology description
        source.flatMapValues(
                // split input String into an array of words using "\\W+" as delimiter
                value -> Arrays.asList(value.toLowerCase(Locale.getDefault()).split("\\W+")))
                // Group the new records. The new records are all single words.
                .groupBy((key, value) -> value)
                // count the occurrences, using Materialized (todo figure out materialized)
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"))
                // this creates a Ktable, we need to cast it to a KStream
                .toStream()
                // stream output to a topic that is serialized with key = String, value = long
                .to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));

        // build defined stream into a topology
        final Topology topology = builder.build();

        // provide user with feedback
        Logging.log(topology.describe().toString(),TAG);

        // create a generified Stream with the created topology
        GenericStream wordCountStream = new GenericStream("streams-wordcount",Config.getLocalBootstrapServersConfig(),
                Serdes.String().getClass(), Serdes.String().getClass(), topology);


        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                wordCountStream.close();
                latch.countDown();
            }
        });

        try {
            wordCountStream.run();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);

    }
}
