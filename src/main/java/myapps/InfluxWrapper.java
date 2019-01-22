package myapps;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class InfluxWrapper {
    public static void query(String baseUrl, String database, String query)
    throws Exception {
        // set up connection to localhost URL
        HttpURLConnection con = (HttpURLConnection) new URL(baseUrl + "/query?").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // set up request params
        Map<String, String> parameters = new HashMap<>();
        parameters.put("db",database);
        parameters.put("q", query);

        // perform HTTP request
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

    }


    public void processResponse(HttpURLConnection con) throws Exception{
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
    }
}