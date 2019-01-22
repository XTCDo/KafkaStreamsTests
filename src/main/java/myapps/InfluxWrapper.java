package myapps;


import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class InfluxWrapper {
    // http naar localhost:8086
    // db = kafka test
    // get request

    // --data-urlencode 'q=SELECT * FROM weather'

    public static void main(String[] args){
        try {
            // set up connection to localhost URL
            URL url = new URL("http://localhost:8086/query?");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            // request params
            Map<String, String> parameters = new HashMap<>();
            parameters.put("db","kafka_test");
            parameters.put("q", "SELECT * FROM weather");

            // request is url-encoded
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

