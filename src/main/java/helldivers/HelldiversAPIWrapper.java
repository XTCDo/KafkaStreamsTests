package helldivers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import util.Logging;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;

public class HelldiversAPIWrapper {
    // Link to API
    // https://docs.google.com/document/d/11BH152Tx7YpWOlfT69Ad2anG8wwlt6xOE3VO_YHC2mQ/edit#heading=h.trtt0zalsa2
    public  static Map doHTTPRequest(String action){
        final String TAG = "HelldiversAPIWrapper";
        try {
            URL url = new URL("https://api.helldiversgame.com/1.0/");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("action", action);
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            outputStream.flush();
            outputStream.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while((inputLine = in.readLine()) != null){
                content.append(inputLine);
            }

            Gson gson = new Gson();
            Map apiResponse = gson.fromJson(content.toString(), Map.class);
            in.close();
            return apiResponse;
        } catch (Exception e){
            Logging.error(e, TAG);
            return null;
        }
    }

    public static List<CampaignStatus> getCampaignStatus(){
        List<Map> campaignStatusList = (List) doHTTPRequest("get_campaign_status").get("campaign_status");
        List<CampaignStatus> campaignStatusObjectList = new ArrayList<>();
        campaignStatusList.forEach(status ->{
            CampaignStatus campaignStatus = new CampaignStatus(
                    (int) Math.round((double) status.get("season")),
                    (int) status.get("points"),
                    (int) status.get("points_taken"),
                    (int) status.get("points_max"),
                    (String) status.get("status"),
                    (int) status.get("introduction_order")
            );
            campaignStatusObjectList.add(campaignStatus);
        });

        return campaignStatusObjectList;
    }

    public static Map getAttackEvents() {
        return (Map) doHTTPRequest("get_campaign_status").get("attack_event");
    }

    public static Map getDefendEvents() {
        return (Map) doHTTPRequest("get_campaign_status").get("defend_event");
    }

}

class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}