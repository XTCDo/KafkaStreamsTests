package influx;

import java.util.HashMap;
import java.util.Map;

public class InfluxDAOTest {
    public static void main(String[] args){
        // Get data access object
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");

        dao.ping();
        
        // Make some tags
        Map<String, String> tags = new HashMap<>();
        tags.put("location", "russia");

        // Make some data
        Map<String, String> fields = new HashMap<>();
        fields.put("temperature", "80");

        // Insert into database
        System.out.println("Inserting");
        dao.insertRecord("kafka_test", "weather", tags, fields);
        System.out.println("Done.");

        // testing simple insert and Record together
        Record earth= new Record();
        earth.addTag("location","Terra");
        earth.addTag("color","light blue");

        earth.addField("dist_to_sun",1); // in AU
        earth.addField("gravity", 9.81);
        earth.addField("temperature",20);

        Record mars= new Record();
        mars.addTag("location","Mars");
        mars.addTag("color","red brown");

        mars.addField("gravity", 9.81/3);
        mars.addField("temperature",-150);
        mars.addField("dist_to_sun",1.5);

        Record venus= new Record();
        venus.addTag("location","Venus");
        venus.addTag("color", "white");

        venus.addField("gravity", 8.87);
        venus.addField("temperature", 400);
        venus.addField("dist_to_sun",0.723);

        dao.simpleInsertRecord("kafka_test","planets",earth);
        dao.simpleInsertRecord("kafka_test","planets",mars);
        dao.simpleInsertRecord("kafka_test","planets",venus);
    }
}
