package helldivers;

import util.Logging;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class HelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "HelldiversAPIWrapper";
        try {
            URL url = new URL("https://api.helldiversgame.com/1.0/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
        } catch (Exception e){
            Logging.log(Level.SEVERE, e.getMessage(), TAG);
        }
    }
}
