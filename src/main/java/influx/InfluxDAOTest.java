package influx;

import org.influxdb.dto.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfluxDAOTest {
    public static void main(String[] args){
        // Get data access object
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");

        // Create a list of point objects
        List<Point> planets = new ArrayList<>();

        planets.add(Point.measurement("planets")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("name","Terra")
                .addField("capitol","Diepenbeek")
                .addField("color", "blue")
                .addField("gravity", 9.81f)
                .addField("dist_to_sun", 1f)
                .addField("temperature",310)
                .build());

        planets.add(Point.measurement("planets")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("name","Mercury")
                .addField("capitol","Sydney")
                .addField("color", "brown")
                .addField("gravity", 3.7f)
                .addField("dist_to_sun", 0.39f)
                .addField("temperature",452)
                .build());

        planets.add(Point.measurement("planets")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("name","Venus")
                .addField("color", "light_blue")
                .addField("gravity", 8.87f)
                .addField("dist_to_sun", 0.723f)
                .addField("temperature",726)
                .build());


        // Put the point into the database
        dao.writePointList("kafka_test",planets);

        dao.select("kafka_test","*","planets");
    }
}
