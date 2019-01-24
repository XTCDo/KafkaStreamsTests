package influx;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Arrays;
import java.util.List;

public class InfluxDAO {
    // DAO-specific vars
    private String targetUrl;
    private static final String DEFAULT_RP = "autogen";

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



    public String select(String database, String target, String table){
        String selectQuery = new StringBuilder()
                .append("SELECT").append(target)
                .append("FROM").append(table)
                .toString();
        return query(database,selectQuery);
    }

    public String select(String database, String target, String table, String conditional){
        String selectQuery = new StringBuilder()
                .append("SELECT").append(target)
                .append("FROM").append(table)
                .append("WHERE").append(conditional)
                .toString();
        return query(database,selectQuery);
    }

    public void writePointList(String database, List<Point> points){
        writePointList(database, points, DEFAULT_RP);
    }

    public void writePointList(String database, List<Point> points, String retentionPolicy){
        InfluxDB ifdb = connect();
        BatchPoints batch = BatchPoints
                .database(database)
                .tag("async", "true")
                .retentionPolicy(retentionPolicy)
                .build();

        points.forEach(batch::point);
        ifdb.write(batch);

        ifdb.close();
    }

    /**
     * uploads a point to the specified database, using default retentionPolicy: autogen
     * @param database  name of database
     * @param point     point to write
     */
    public void writePoint(String database, Point point){
        writePoint(database, point, DEFAULT_RP);
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