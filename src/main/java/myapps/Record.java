package myapps;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import org.apache.kafka.common.protocol.types.Field;
//todo tr " " to "_" in tags
// todo edit RecordTest to test for string fields
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Record {
    private Map<String, String> tags;
    private Map<String, Object> fields;

    // === constructors ===
    public Record(){
        this.tags= new HashMap<>();
        this.fields = new HashMap<>();
    }
    public Record(Map<String, String> tags, Map<String, Object> fields){
        this.tags=
                tags.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().replace(" ","_"))
                        );
        this.fields=fields;
    }

    // === publically available functs ==
    public String toString(){
        String tagString=tags.entrySet() // perform stream on entrySet
                .stream()   // start java stream
                .map(entry -> entry.getKey() + "=" + entry.getValue())  // map all entries to function
                .collect(Collectors.joining(","));  // join mapped Strings with delimiter ","
        String fieldString=fields.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" +getFieldString(entry.getKey()))
                .collect(Collectors.joining(","));
        return (tagString+" "+fieldString);
    }

    public void addTag(String key, String tag){
       tags.put(key, tag.replace(" ","_"));
    }

    public void addField(String key, Object value){
        fields.put(key, value);
    }

    public String getTag(String key){
        return tags.get(key);
    }

    public Object getField(Object key){
        return fields.get(key);
    }

    // === private auxiliary functions ===
    private String getFieldString(String key){
        Object field= getField(key);
        String response;
        if (field instanceof String){
            response = quote((String) field);
        }
        else {
            response=field.toString();
        }
        return response;
    }
    private String quote(String source){
        return "\""+source+"\"";
    }

}
