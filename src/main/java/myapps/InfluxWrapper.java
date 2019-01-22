package myapps;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class InfluxWrapper {
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

            // do the request, specified by out
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();

            // do something with the response specified by in
            int status = con.getResponseCode();
            switch (status) {
                case 200:
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    System.out.println(content);
                default:
                    System.out.println(status);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

