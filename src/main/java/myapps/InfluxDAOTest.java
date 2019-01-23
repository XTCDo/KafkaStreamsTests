package myapps;

import java.util.HashMap;
import java.util.Map;

public class InfluxDAOTest {
    public static void main(String[] args){
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");
        Map<String, String> data = new HashMap<String, String>();
        data.put("temperature", "0");
        data.put("location", "russia");
        dao.insertRecord("kafka_test", "weather", data);
    }
}
