package influx;

import org.influxdb.dto.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InfluxDAOTest {
    public static void main(String[] args){
        // Get data access object
        InfluxDAO dao = new InfluxDAO("http://localhost:8086/write/");

        // === pinging database ===
        //dao.ping();

        // === testing basic query ===
        //dao.query("kafka_test","SELECT * FROM planets");

        // === testing insert fun===
        // Make some tags
        Map<String, String> tags = new HashMap<>();
        tags.put("location", "russia");

        // Make some data
        Map<String, String> fields = new HashMap<>();
        fields.put("temperature", "80");


        // insert point to database
        Point point = Point.measurement("planets")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("location","Terra")
                .addField("color", "blue")
                .addField("gravity", 9.81)
                .addField("dist_to_sun", 1)
                .build();
        dao.writeRecord("kafka_test",point);
    }
}
