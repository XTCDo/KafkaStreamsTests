package myapps;

import java.util.HashMap;
import java.util.Map;

public class InfluxDAOTest {
    public static void main(String[] args){
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");
        Map<String, String> tags = new HashMap<>();
        tags.put("location", "russia");
        Map<String, String> fields = new HashMap<>();
        fields.put("temperature", "80");
        System.out.println("Inserting");
        dao.insertRecord("kafka_test", "weather", tags, fields);
        System.out.println("Done.");
    }
}
