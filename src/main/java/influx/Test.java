package influx;

import javax.print.DocFlavor;
import java.util.Map;

public class Test {
    public static void main(String[] args){
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");

        System.out.println(dao.query("kafka_test", "SELECT * FROM planets"));
    }
}
