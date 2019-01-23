package myapps;

import org.apache.kafka.common.protocol.types.Field;

import java.util.HashMap;
import java.util.Map;

public class Record {
    private Map<String, String> tags;
    private Map<String, Object> fields;

    // === constructors ===
    public Record(){
        this.tags= new HashMap<>();
        this.fields = new HashMap<>();
    }
    public Record(Map<String, String> tags, Map<String, Object> fields){
        this.tags=tags;
        this.fields=fields;
    }

    // === publically available functs ==
    public String toString(){
        return null;
    }

    public void addTag(String key, String tag){
       tags.put(key, tag);
    }

    public void addField(String key, Object value){
        fields.put(key, value);
    }
    // === private auxiliary functions ===


}
