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
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import util.Config;
import util.Logging;

import java.util.concurrent.CountDownLatch;

/**
 * In this example, we implement a simple LineSplit program using the high-level Streams DSL
 * that reads from a source topic "kafka.streams-plaintext-input", where the values of messages represent lines of text,
 * and writes the messages as-is into a sink topic "kafka.streams-pipe-output".
 */
public class ReverseRecord {
    private static final String TAG = "ReverseRecord";

    public static void main(String[] args) throws Exception {

        final StreamsBuilder builder = new StreamsBuilder();

        // Get a source stream from the topic 'kafka.streams-plaintext-input'
        KStream<String, String> source = builder.stream("streams-plaintext-input");

        //reverse the input string and stream to output topic
        source.mapValues(
                value -> new StringBuilder(value).reverse().toString())
                .to("streams-reverserecord-output");

        final Topology topology = builder.build();
        Logging.log(topology.describe().toString(),TAG);

        GenericStream reverseRecordStream = new GenericStream("streams-reverserecord", Config.getLocalBootstrapServersConfig(),
                Serdes.String().getClass(), Serdes.String().getClass(), topology);


        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                reverseRecordStream.close();
                latch.countDown();
            }
        });

        try {
            reverseRecordStream.run();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}