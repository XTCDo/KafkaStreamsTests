package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class GenericProducer<K, V> {
    private Properties properties;
    private String topic;
    private Producer<K, V> producer;

    public GenericProducer(String topic,
                           String bootStrapServer,
                           Object keySerializerClass,
                           Object valueSerializerClass,
                           String acks,
                           int retries,
                           int batchSize,
                           int lingerMS,
                           int bufferMemory) {
        this.topic = topic;
        properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClass);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);
        properties.put(ProducerConfig.ACKS_CONFIG, acks);
        properties.put(ProducerConfig.RETRIES_CONFIG, retries);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMS);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        producer = new KafkaProducer<K, V>(properties);
    }

    public GenericProducer(String topic, String bootStrapServer){
        this(topic, bootStrapServer, StringSerializer.class, StringSerializer.class,
                "all", 0, 16384, 1, 33554432);
    }

    public Producer getProducer() {
        return producer;
    }
    public String getTopic() {
        return topic;
    }
}
