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
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import util.Config;
import util.Logging;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * In this example, we implement a simple LineSplit program using the high-level Streams DSL
 * that reads from a source topic "streams-plaintext-input", where the values of messages represent lines of text,
 * and writes the messages as-is into a sink topic "kafka.streams-pipe-output".
 */
public class RecordLength {
    private static final String TAG = "RecordLength";

    public static void main(String[] args) throws Exception {

        // as always, start with a streamsBuilder
        StreamsBuilder builder = new StreamsBuilder();

        // Get a source stream from the topic 'kafka.streams-plaintext-input'
        KStream<String, String> source = builder.stream("streams-plaintext-input");

        // Get the length of the string in the input record and then turn
        // that number into a string
        source.mapValues(value -> Integer.toString(value.length())) // calculate the length
                .to("streams-recordlength-output"); // send to output topic

        // finally, construct the topology using the streams builder
        final Topology topology = builder.build();

        Logging.log(topology.describe().toString(),TAG);

        GenericStream RecordLengthStream = new GenericStream("streams-recordlength", Config.getLocalBootstrapServersConfig(),
                Serdes.String().getClass(), Serdes.String().getClass(),topology);

        // creating countDownLatch for Thread handling
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                RecordLengthStream.close();
                latch.countDown();
            }
        });

        try {
            RecordLengthStream.run();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}