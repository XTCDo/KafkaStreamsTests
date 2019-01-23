package influx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleHttpRequest {
    public static void main(String[] args){

        try {
            String url = "http://localhost:8086/ping";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("Sending GET request");
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            System.out.println(con.getHeaderField("X-Influxdb-Version"));

            in.close();

            System.out.println(response.toString());

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
