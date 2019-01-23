package influx;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    // === public functions ===
    public void ping(){
        System.out.println("sending ping request to"+ targetUrl);
        InfluxDB ifdb = connect();
        Pong pong = ifdb.ping();
        ifdb.close();
        System.out.println(pong.toString());
    }
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
        StringBuilder responseStringBuilder = new StringBuilder();
        ifdb.query(query, responseStringBuilder::append, Throwable::printStackTrace);
        System.out.println(new String(responseStringBuilder));
        // close connection
        ifdb.close();

        return responseStringBuilder.toString();
    }

    /**
     *  simplified record insert function
     * @param database
     * @param table
     * @param record
     */
    public void simpleInsertRecord(String database,String table, Record record){
        try{
            // Build the query string
            StringBuilder queryStringBuilder = new StringBuilder();
            queryStringBuilder.append("INSERT ")
                    .append(table).append(",")
                    .append(record.toString());
            String queryString = new String(queryStringBuilder);
            System.out.println("query built:\t" + queryString);

            // Perform query and print response
            String response = query(database, queryString);
            System.out.println("response:\t" + response);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Performs an INSERT query on a given database in a given table
     * @param database The name of the database to insert into
     * @param table The table to insert to
     * @param tags The tags that should be inserted
     * @param fields The values/fields that should be inserted
     * @return true if no exception
     */
    public boolean insertRecord(String database, String table, Map<String, String> tags, Map<String, String> fields){
        try {
            // Convert tags and fields to strings
            String tagsAsString = tagsMapToString(tags);
            String fieldsAsString = fieldsMapToString(fields);

            // Build the query string
            StringBuilder queryStringBuilder = new StringBuilder();
            queryStringBuilder.append("INSERT ")
                    .append(table).append(",")
                    .append(tagsAsString)
                    .append(" ")
                    .append(fieldsAsString);
            String queryString = new String(queryStringBuilder);
            System.out.println("query built: " + queryString);

            // Perform query and print response
            String response = query(database, queryString);
            System.out.println("response:\t" + response);
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Transforms a Map of fields into a String to be used in a query
     * Values that match the regex "\\d+" are not surrounded by double quotes
     * Values that don't match the regex "\\d+" are surrounded by double quotes
     * @param map The map that should be transformed into a String
     * @return The given Map as a String to use in a query
     */
    private String fieldsMapToString(Map<String, String> map){
        // Get keys from given map
        Set<String> keySet = map.keySet();
        List<String> keyValuePairsAsString = new ArrayList<String>();

        // Iterate through keys and add strings to list
        for(String key : keySet){
            String value = map.get(key);
            if(isNumeric(value)){
                // Numbers are not surrounded by double quotes
                keyValuePairsAsString.add(key + "=" + value);
            } else {
                // Strings are surrounded by double quotes
                keyValuePairsAsString.add(key + "=\"" + value + "\"");
            }
        }

        // Join the list together and use a comma as delimiter
        return String.join(",", keyValuePairsAsString);
    }

    /**
     * Transforms a Map of tags into a string to be used in a query
     * @param map The Map that should be tranformed into a String
     * @return The given Map as a String to use in a query
     */
    private String tagsMapToString(Map<String, String> map){
        // Get keys from given map
        Set<String> keySet = map.keySet();
        List<String> keyValuePairsAsStrings = new ArrayList<String>();

        // Iterate through keys and add strings to list
        for (String key : keySet){
            keyValuePairsAsStrings.add(key + "=" + map.get(key));
        }

        // Join the list together and use a comma as delimiter
        return String.join(",", keyValuePairsAsStrings);
    }

    /**
     * Checks if a string is numeric
     * @param value The string to check
     * @return true if value is numeric
     */
    private boolean isNumeric(String value){
        return value.matches("\\d+");
    }

}