package myapps;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxAPIClientTest {
    public static void main(String[] args){
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086");
        String dbName = "kafka_test";
        influxDB.setDatabase(dbName);
        String rpName = "aRetentionPolicy";
        influxDB.createRetentionPolicy(rpName, dbName, "30d", "30m", 2, true);
        influxDB.setRetentionPolicy(rpName);
        influxDB.enableBatch(BatchOptions.DEFAULTS);
        System.out.println("about to write");
        influxDB.write(Point.measurement("weather")
            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .addField("location", "midwest-us")
            .addField("temperature", 90)
            .build());
        System.out.print("done writing");
        influxDB.dropRetentionPolicy(rpName,dbName);
    }
}
