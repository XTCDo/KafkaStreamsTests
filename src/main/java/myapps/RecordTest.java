package myapps;

import java.rmi.MarshalException;
import java.util.HashMap;
import java.util.Map;

/**
 * demonstrates and tests Record class functionality
 */
public class RecordTest {
    public static void main(String args[]){
        // instantiating an empty record and populating it with tags & fields
        Record earth= new Record();
        earth.addTag("location","Terra");
        earth.addTag("capitol", "Diepenbeek");

        earth.addField("gravity", 9.81);
        earth.addField("temperature",20);

        // instantiating a record with predefined tags and fields
        Map<String, String> marsTags = new HashMap<>();
        marsTags.put("location","Mars");
        marsTags.put("capitol", "Vallis Marineris");

        Map<String, Object> marsFields = new HashMap<>();
        marsFields.put("gravity", 9.81/3);
        marsFields.put("temperature",-150);
        Record mars= new Record(marsTags,marsFields);

        // testing getter functions
        System.out.println(earth.getTag("location")+":\t"+earth.getField("gravity"));
        System.out.println(mars.getTag("location")+":\t"+mars.getField("gravity"));
        
        System.out.println(earth.toString());
        System.out.println(mars.toString());
    }
}
