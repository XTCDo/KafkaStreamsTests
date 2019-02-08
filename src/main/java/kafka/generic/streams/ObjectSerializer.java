package kafka.generic.streams;

import org.apache.kafka.common.serialization.Serializer;
import util.Logging;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;

public class ObjectSerializer implements Serializer {
    private static final String TAG = "Serializer";
    /**
     * does nothing
     * @param map
     * @param b
     */
    @Override
    public void configure(Map map, boolean b) {
        //todo figure out what to do here
    }

    /**
     * The core of this class: takes java object as input, converts to a byte array of serialized data
     * @param topic topic associated with data
     * @param object input java object
     * @return serialized data representation of input object
     */
    @Override
    public byte[] serialize(String topic, Object object) {
        // catch null objects early
        if (object == null){
            return null;
        }
        Logging.log(Level.FINER,"received: "+ object.toString(), TAG);

        byte[] bArray = null;
        try (   ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(outputStream)){

            out.writeObject(object);
            //bArray = outputStream.toByteArray();
            bArray = outputStream.toByteArray();
            Logging.log(Level.FINER,"parsed to(string): "+ outputStream.toString(), TAG);
        } catch (Exception e){
            Logging.log(Level.SEVERE, Arrays.toString(e.getStackTrace()),TAG);
        }

        Logging.log(Level.FINER,"parsed to: "+ Arrays.toString(bArray), TAG);
        return bArray;
}

    /**
     * does nothing temporarily
     */
    @Override
    public void close() {

    }
}
