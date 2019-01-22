package myapps;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
// curl -XPOST 'http://localhost:8086/write?db=kafka_test' --data-binary 'weather,location=us-midwest temperature=8'
public class InfluxInsertTest {
    public static void main(String[] args){
        try {
            // set up connection to localhost URL
            URL url = new URL("http://localhost:8086/write?");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            // request params
            Map<String, String> parameters = new HashMap<>();
            parameters.put("db","kafka_test");
            parameters.put("q", "weather,location=us-midwest temperature=8");

            // request is url-encoded
            con.setRequestProperty("Content-Type", "form/multipart");

            // do the request, specified by out
            con.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();

            // do something with the response specified by in
            int status = con.getResponseCode();
                if (status<300){ // 2XX is okay range for HTTP
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    System.out.println(content);

            }else{
                System.out.println(status);
            }

            con.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}