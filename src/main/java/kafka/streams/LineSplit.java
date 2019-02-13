/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kafka.streams;

import kafka.generic.streams.GenericStream;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import util.Config;
import util.Logging;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * In this example, we implement a simple LineSplit program using the high-level Streams DSL
 * that reads from a source topic "kafka.streams-plaintext-input", where the values of messages represent lines of text,
 * and writes the messages as-is into a sink topic "streams-pipe-output".
 */
public class LineSplit {
    private static final String TAG = "LineSplit";
    public static void main(String[] args) throws Exception {
        final StreamsBuilder builder = new StreamsBuilder();

        // Get a source stream from the topic 'kafka.streams-plaintext-input'
        KStream<String, String> source = builder.stream("streams-plaintext-input");

        // Split every record into separate words with delimiter \\W+.
        // This may result in multiple output records per input record.
        source.flatMapValues(
                value -> Arrays.asList(value.split("\\W+")))
                // Send output records to the 'kafka.streams-linesplit-output" topic
                .to("streams-linesplit-output");

        // build and describe topology
        final Topology topology = builder.build();
        Logging.log(topology.describe().toString(),TAG);

        GenericStream lineSplitStream = new GenericStream("streams-linesplit", Config.getLocalBootstrapServersConfig(),
                Serdes.String().getClass(), Serdes.String().getClass(), topology);
        
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                lineSplitStream.close();
                latch.countDown();
            }
        });

        try {
            lineSplitStream.run();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
