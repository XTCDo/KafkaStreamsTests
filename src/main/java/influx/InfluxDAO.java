package influx;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

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
    /*  todo add more general functions for specific queries/operations
        write batch of points
        CREATE
        SELECT
        DROP
        EXISTS?
        DELETE
     */

    /**
     * pings the target URL of this object
     */
    public void ping(){
        System.out.println("sending ping request to:\t"+ targetUrl);
        InfluxDB ifdb = connect();
        System.out.println("response time:\t"+ifdb.ping().getResponseTime());
        ifdb.close();
    }

    public void exists(String database, String table){
        
    }

    /**
     * uploads a point to the specified database, using default retentionPolicy: autogen
     * @param database  name of database
     * @param point     point to write
     */
    public void writePoint(String database, Point point){
        writePoint(database, point, "autogen");
    }

    /**
     *   uploads a point to the specified database, using specified retention policy
     * @param database          name of database
     * @param point             point to write
     * @param retentionPolicy   retention policy to maintain(how long to save etc...)
     */
    public void writePoint(String database, Point point, String retentionPolicy){
        InfluxDB ifdb = connect();
        ifdb.write(database, retentionPolicy, point);
        ifdb.close();
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
        ifdb.setDatabase(database); // is this necessary?
        ifdb.enableBatch(BatchOptions.DEFAULTS); // todo figure out if this needs to exist

        // prepare query
        Query query = new Query(inputquery, database);
        System.out.println("performing query:\t"+query.getCommand());

        // perform query and immediately process response as a string
        QueryResult result= ifdb.query(query);  // todo improve this to better process responses

        String responseString = result.getResults().toString();
        System.out.println("got response:\t"+responseString);

        //ifdb.flush();   // put in comments to test if it can be removed
        // close connection
        ifdb.close();

        return responseString;
    }
}