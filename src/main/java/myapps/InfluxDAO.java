package myapps;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

import javax.print.DocFlavor;
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

    public boolean insertRecord(String database, String table, Map<String, String> tags, Map<String, String> fields){
        //"INSERT weather,location=us-midwest temperature=8"
        try {
            String tagsAsString = tagsMapToString(tags);
            String fieldsAsString = fieldsMapToString(fields);

            StringBuilder queryStringBuilder = new StringBuilder();
            queryStringBuilder.append("INSERT ")
                    .append(table).append(",")
                    .append(tagsAsString)
                    .append(" ")
                    .append(fieldsAsString);
            
            String queryString = new String(queryStringBuilder);
            System.out.println("query built:\t"+queryString);
            System.out.println("response:\t"+this.query(database, queryString));
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }

    private String fieldsMapToString(Map<String, String> map){
        Set<String> keySet = map.keySet();
        List<String> keyValuePairsAsString = new ArrayList<String>();
        for(String key : keySet){
            String value = map.get(key);
            if(isNumeric(value)){
                keyValuePairsAsString.add(key + "=" + value);
            } else {
                keyValuePairsAsString.add(key + "=\"" + value + "\"");
            }
        }
        return String.join(",", keyValuePairsAsString);
    }

    private String tagsMapToString(Map<String, String> map){
        Set<String> keySet = map.keySet();
        List<String> keyValuePairsAsStrings = new ArrayList<String>();
        for (String key : keySet){
            keyValuePairsAsStrings.add(key + "=" + map.get(key));
        }
        return String.join(",", keyValuePairsAsStrings);
    }

    private boolean isNumeric(String value){
        return value.matches("\\d+");
    }

}