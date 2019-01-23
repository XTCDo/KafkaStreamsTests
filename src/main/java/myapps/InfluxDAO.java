package myapps;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        //"INSERT weather,location=us-midwest temperature=8"
        try {
            Set<String> keySet = values.keySet();
            List<String> keyValuePairsAsStrings = new ArrayList<>();
            for (String key : keySet) {
                keyValuePairsAsStrings.add(key + "=" + values.get(key));
            }
            String keyValuePairsAsString = String.join(" ", keyValuePairsAsStrings);
            StringBuilder queryStringBuilder = new StringBuilder();
            queryStringBuilder.append("INSERT ");
            queryStringBuilder.append(table).append(",");
            queryStringBuilder.append(keyValuePairsAsString);
            String queryString = new String(queryStringBuilder);
            System.out.println("query built:\t"+queryString);
            System.out.println("response:\t"+this.query(database, queryString));
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }

}