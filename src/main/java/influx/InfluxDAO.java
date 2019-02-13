package influx;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;

public class InfluxDAO {
    private String targetUrl;
    private InfluxDB influxDB;
    private static final String DEFAULT_RP = "autogen";

    public InfluxDAO(String urlString){
        targetUrl = urlString;
        influxDB = connect();
    }

    private InfluxDB connect() {
        return InfluxDBFactory.connect(targetUrl);
    }

    /**
     * pings the target URL of this object
     */
    public void ping(){
        System.out.println("sending ping request to:\t" + targetUrl);
        System.out.println("response time:\t" + influxDB.ping().getResponseTime());
    }

    public String select(String database, String target, String table){
        String selectQuery = new StringBuilder()
                .append("SELECT ").append(target)
                .append("FROM ").append(table)
                .toString();
        return query(database,selectQuery);
    }

    public String select(String database, String target, String table, String conditional){
        String selectQuery = new StringBuilder()
                .append("SELECT ").append(target)
                .append("FROM ").append(table)
                .append("WHERE ").append(conditional)
                .toString();
        return query(database,selectQuery);
    }

    public void writePointList(String database, List<Point> points){
        writePointList(database, points, DEFAULT_RP);
    }

    public void writePointList(String database, List<Point> points, String retentionPolicy){
        BatchPoints batch = BatchPoints
                .database(database)
                .retentionPolicy(retentionPolicy)
                .build();

        points.forEach(batch::point);
        influxDB.write(batch);
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
        influxDB.write(database, retentionPolicy, point);
    }

    /**
     * performs a simple query on a given database
     * @param database      database name
     * @param inputQuery    query to perform
     */
    public String query(String database, String inputQuery){
        // connect to influxdb
        influxDB.setDatabase(database); // is this necessary?
        influxDB.enableBatch(BatchOptions.DEFAULTS); // todo figure out if this needs to exist

        // prepare query
        Query query = new Query(inputQuery, database);
        System.out.println("performing query:\t"+query.getCommand());

        // perform query and immediately process response as a string
        QueryResult result = influxDB.query(query);  // todo improve this to better process responses

        String responseString = result.getResults().toString();
        System.out.println("got response:\t" + responseString);

        //ifdb.flush();   // put in comments to test if it can be removed
        // close connection

        return responseString;
    }

    public void close(){
        influxDB.close();
    }
}