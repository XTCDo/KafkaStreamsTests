package myapps;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;



public class InfluxQueryTestAlt {

    public static void main(String[] args){
        // connect to influxDB
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086");
        // what database to use
        String dbName = "kafka_test";

        // connection stuff
        influxDB.setDatabase(dbName);
        influxDB.enableBatch(BatchOptions.DEFAULTS);

        Query query = new Query("SELECT * FROM weather", dbName);
        influxDB.query(query, queryResult -> {
            System.out.println(queryResult);
        },throwable -> {
            throwable.printStackTrace();
        });
        influxDB.close();
    }
}
