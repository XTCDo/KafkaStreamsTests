package myapps;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

public class InfluxWrapper {

    /**
     * performs a simple query on a given database
     * @param url           url of database to connect with
     * @param database      database name
     * @param inputquery    query to perform
     */
    public static void query(String url, String database, String inputquery){
        InfluxDB influxDB = InfluxDBFactory.connect(url);

        // connection stuff
        influxDB.setDatabase(database);
        influxDB.enableBatch(BatchOptions.DEFAULTS);

        Query query = new Query(inputquery, database);
        influxDB.query(query, System.out::println, Throwable::printStackTrace);
        influxDB.close();
    }

}