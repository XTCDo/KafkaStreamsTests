package kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public abstract class AbstractConsumer {
    protected Properties properties;
    protected String topic;

    /**
     * Constructor for an AbstractConsumer with default Properties
     * @param topic The topic the consumer should read form
     * @param groupIdConfig
     */
    public AbstractConsumer(String topic, String groupIdConfig){
        this(topic, groupIdConfig, true, 1000, "localhost:9092",
                StringDeserializer.class, StringDeserializer.class);
    }

    public AbstractConsumer(String topic, String groupIdConfig,
                            boolean enableAutoCommitConfig,
                            int autoCommitIntervalMSConfig,
                            String bootstrapServersConfig,
                            Object keyDeserializerClass,
                            Object valueDeserializerClass){
        this.topic = topic;
        properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdConfig);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommitConfig);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMSConfig);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClass);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClass);
    }

    public abstract void consume();
    public abstract void handleException(Exception e);
    public void main(){
        Thread consumerThread = new Thread(() -> {
            try {
                consume();
            } catch(Exception e) {
                handleException(e);
            }
        });

        try {
            consumerThread.start();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }
}
