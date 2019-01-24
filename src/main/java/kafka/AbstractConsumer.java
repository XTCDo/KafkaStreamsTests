package kafka;

import influx.InfluxDAO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public abstract class AbstractConsumer {
    protected Properties properties;
    private String connectionString;
    protected String topic;
    protected InfluxDAO influxDAO;
    public AbstractConsumer(String topic, String connectionString, String groupIdConfig){
        this(topic, connectionString, groupIdConfig, true, 1000, "localhost:9092",
                StringDeserializer.class, StringDeserializer.class);
    }

    public AbstractConsumer(String topic, String connectionString, String groupIdConfig,
                            boolean enableAutoCommitConfig,
                            int autoCommitIntervalMSConfig,
                            String bootstrapServersConfig,
                            Object keyDeserializerClass,
                            Object valueDeserializerClass){
        this.topic = topic;
        this.connectionString = connectionString;
        properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdConfig);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommitConfig);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMSConfig);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClass);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClass);
        influxDAO = new InfluxDAO(connectionString);
    }

    public abstract void consume();

    public void main(){
        influxDAO = new InfluxDAO(connectionString);
        Thread consumerThread = new Thread(() -> {
            try {
                consume();
            } catch(Exception e) {
                e.printStackTrace();
                influxDAO.close();
            }
        });

        try {
            consumerThread.start();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }
}
