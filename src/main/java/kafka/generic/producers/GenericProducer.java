package kafka.generic.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * A generic Kafka Producer
 * @param <K> The type that the Key of the record should have
 * @param <V> The type that the Value of the record should have
 */
public class GenericProducer<K, V> {
    private Properties properties;
    private String topic;
    private Producer<K, V> producer;

    /**
     * Constructor for a GenericProducer with much configuration
     * @param topic The topic the GenericProducer should send messages to
     * @param bootStrapServer The hostname of the server that the GenericProducer should use
     * @param keySerializerClass Class of the Serializer that will be used for the key
     * @param valueSerializerClass Class of the Serializer that will be used for the value
     * @param acks TODO figure out what these actually mean
     * @param retries
     * @param batchSize
     * @param lingerMS
     * @param bufferMemory
     */
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

    /**
     * Constructor for a GenericProducer with resonable default configuration
     * @param topic The topic the GenericProducer should send messages to
     * @param bootStrapServer The hostname of the server that the GenericProducer should use
     */
    public GenericProducer(String topic, String bootStrapServer) {
        this(topic, bootStrapServer, StringSerializer.class, StringSerializer.class,
                "all", 0, 16384, 1, 33554432);
    }

    /**
     * Gets the Producer
     * @return The Producer
     */
    public Producer getProducer() {
        return producer;
    }

    /**
     * Gets the topic that messages should be sent to
     * @return The topic that messages should be sent to
     */
    public String getTopic() {
        return topic;
    }
}
