package myapps;

import java.util.HashMap;
import java.util.Map;

public class InfluxDAOTest {
    public static void main(String[] args){
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");
        Map<String, String> data = new HashMap<String, String>();
        data.put("location", "russia");
        data.put("temperature", "-500");
        dao.insertRecord("kafka_test", "weather", data);
    }
}
