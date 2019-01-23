package influx;

import org.influxdb.dto.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InfluxDAOTest {
    public static void main(String[] args){
        // Get data access object
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");

        // Create a Point object
        Point point = Point.measurement("planets")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("location","Terra")
                .addField("color", "blue")
                .addField("gravity", 9.81)
                .addField("dist_to_sun", 1f)
                .build();

        // Put the point into the database
        dao.writeRecord("kafka_test",point);
    }
}
