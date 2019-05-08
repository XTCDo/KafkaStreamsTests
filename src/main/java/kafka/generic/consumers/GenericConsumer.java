package kafka.generic.consumers;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;

public class GenericConsumer<K, V> {
    private Properties properties;
    private List<String> topics;
    private Consumer<K,V> consumer;

    public GenericConsumer(List<String> topics,
                           String bootStrapServers,
                           String groupId,
                           Object keyDeserializerClass,
                           Object valueDeserializerClass,
                           boolean enableAutoCommit,
                           int autoCommitIntervalMS) {
        properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClass);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClass);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMS);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(topics);
        this.topics = topics;
    }

    public GenericConsumer(String topic,
                           String bootStrapServers,
                           String groupId,
                           Object keyDeserializerClass,
                           Object valueDeserializerClass,
                           boolean enableAutoCommit,
                           int autoCommitIntervalMS) {
        this(Collections.singletonList(topic), bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS);
    }

    public GenericConsumer(String topic, String bootStrapServer, String groupId){
        this(Collections.singletonList(topic), bootStrapServer, groupId);
    }

    public GenericConsumer(List<String> topics, String bootStrapServer, String groupId) {
        this(topics, bootStrapServer, groupId, StringDeserializer.class, StringDeserializer.class,
                true, 1000);
    }

    public List<String> getTopics(){
        return topics;
    }

    public Consumer<K, V> getConsumer(){
        return consumer;
    }
}

