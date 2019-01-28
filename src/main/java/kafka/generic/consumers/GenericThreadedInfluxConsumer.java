package kafka.generic.consumers;

import influx.InfluxDAO;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class GenericThreadedInfluxConsumer<K, V> extends GenericThreadedConsumer<K, V> {
    private InfluxDAO influxDAO;
    public  GenericThreadedInfluxConsumer(String influxURL,
                                          List<String> topics,
                                          String bootStrapServers,
                                          String groupId,
                                          Object keyDeserializerClass,
                                          Object valueDeserializerClass,
                                          boolean enableAutoCommit,
                                          int autoCommitIntervalMS){
        super(topics, bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS);
        makeInfluxDAO(influxURL);
    }

    public GenericThreadedInfluxConsumer(String influxURL,
                                         String topic,
                                         String bootStrapServers,
                                         String groupId,
                                         Object keyDeserializerClass,
                                         Object valueDeserializerClass,
                                         boolean enableAutoCommit,
                                         int autoCommitIntervalMS) {
        this(influxURL, Arrays.asList(topic), bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS);
    }

    public GenericThreadedInfluxConsumer(String influxURL, List<String> topics, String bootStrapServer, String groupId) {
        super(topics, bootStrapServer, groupId);
        makeInfluxDAO(influxURL);
    }

    public GenericThreadedInfluxConsumer(String influxURL, String topic, String bootStrapServer, String groupId) {
        this(influxURL ,Arrays.asList(topic), bootStrapServer, groupId);
    }

    private void makeInfluxDAO(String influxURL){
        influxDAO = new InfluxDAO(influxURL);
    }

    public InfluxDAO getInfluxDAO(){
        return influxDAO;
    }
}
