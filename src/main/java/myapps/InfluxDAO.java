package myapps;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

import java.net.URL;

public class InfluxDAO {
    // DAO-specific vars
    String targetUrl;

    public InfluxDAO(String urlString){
        targetUrl = urlString;
    }

    public InfluxDB connect(){
        return InfluxDBFactory.connect(targetUrl);
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
        StringBuilder responseStringBuilder= new StringBuilder();
        ifdb.query(query, responseStringBuilder::append, Throwable::printStackTrace);

        // close connection
        ifdb.close();

        return responseStringBuilder.toString();
    }

}