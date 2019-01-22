package myapps;


import java.net.HttpURLConnection;
import java.net.URL;

public class InfluxWrapper {
    // http naar localhost:8086
    // db = kafka test
    // get request

    // d--data-urlencode 'q=SELECT * FROM weather'
    public static void main(String[] args){
        URL url = new URL("http://[::1]:8086");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
    }
}