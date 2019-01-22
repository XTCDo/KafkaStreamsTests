package myapps;


import org.apache.kafka.common.protocol.types.Field;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InfluxDAO {
    // DAO-specific vars
    private String targetUrl;

    // === class constructor ===
    public InfluxDAO(String urlString){
        targetUrl = urlString;
    }

    // === private functions ===
    private InfluxDB connect() {
        return InfluxDBFactory.connect(targetUrl);
    }

    private String recordBuilder(Map<String, String> map){
        StringBuilder builder = new StringBuilder();
        return map.entrySet().stream()
                        .map(key -> builder.append(key).append("=").append(key.getValue()))
                        .collect(Collectors.joining());
    }

    // === public functions ===
    /**
     * performs a simple query on a given database
     * @param database      database name
     * @param inputquery    query to perform
     */
    public String query(String database, String inputquery){
        // connect to influxdb
        InfluxDB ifdb = connect();

        // connect
        ifdb.setDatabase(database);
        ifdb.enableBatch(BatchOptions.DEFAULTS);

        // prepare query
        Query query = new Query(inputquery, database);

        // perform the query
        StringBuilder responseStringBuilder= new StringBuilder();
        ifdb.query(query, responseStringBuilder::append, Throwable::printStackTrace);

        // close connection
        ifdb.close();

        return responseStringBuilder.toString();
    }

    public boolean insertRecord(String database, String table, Map<String, String> values){
        "INSERT weather,location=us-midwest temperature=8"
        query(database, "INSERT"+table+);
    }


}