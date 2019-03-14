package helldivers;

import com.google.gson.Gson;
import util.Logging;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class HelldiversAPIWrapper {

    // Link to API
    // https://docs.google.com/document/d/11BH152Tx7YpWOlfT69Ad2anG8wwlt6xOE3VO_YHC2mQ/edit#heading=h.trtt0zalsa2
    public static String getAPIResponse() {
        final String TAG = "HelldiversAPIWrapper";
        try {
            // Create URL object from helldivers api url string
            URL url = new URL("https://api.helldiversgame.com/1.0/");

            // Create a HttpsURLConnection object using new URL object
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); // We are using POST methods

            // Create a Map with the parameters to send with the POST request
            Map<String, String> parameters = new HashMap<>();
            // Add the sole parameter we want to send
            parameters.put("action", "get_campaign_status");

            // This has to be set to true in order to actually send a request body
            // (not all requests have a request body, but ours does)
            connection.setDoOutput(true);

            // Create a DataOutPutStream Object from the OutputStream we are getting from
            // the connection
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            // Write the parameters to the outputStream using the ParameterStringBuilder
            // helper class
            outputStream.writeBytes(ParameterStringBuilder.getParamsString(parameters));

            // Flush the outputStream to our destination
            outputStream.flush();

            // Close the outputStream because it is no longer needed
            outputStream.close();

            // Use BufferedReader to read the result from our request
            BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(connection.getInputStream())); // Use inputStream from connection

            // Define inputLine as empty String
            String inputLine;
            // Create a StringBuffer that will contain the content of the response on our request
            StringBuffer content = new StringBuffer();
            // Fill the buffer with the content from the inputstream
            while ((inputLine = inputStream.readLine()) != null) {
                content.append(inputLine);
            }
            // Close the inputStream because we dont need it anymore
            inputStream.close();


            return content.toString();
        } catch (Exception e) {
            Logging.error(e, TAG);
            return null;
        }
    }


    public static Map getStatus() {
        final String TAG = "HelldiversAPIWrapper";
        try {
            String apiResponseJSONString = getAPIResponse();
            Gson gson = new Gson();
            Map apiResponse = gson.fromJson(apiResponseJSONString, Map.class);
            return apiResponse;

        } catch (Exception e) {
            Logging.error(e, TAG);
            return null;
        }
    }

    public static List<Statistics> getStatistics() {
        return getStatistics(getStatus().get("statistics"));
    }

    public static List<Statistics> getStatistics(Object httpRequestReturnValue) {
        List<Map> statisticsList = (List) httpRequestReturnValue;//doHTTPRequest().get("statistics");
        List<Statistics> statisticsObjectList = new ArrayList<>();
        statisticsList.forEach(statistics -> {
            statisticsObjectList.add(new Statistics(statistics));
        });

        return statisticsObjectList;
    }

    public static List<CampaignStatus> getCampaignStatus() {
        return getCampaignStatus(getStatus().get("campaign_status"));
    }

    public static List<CampaignStatus> getCampaignStatus(Object httpRequestReturnValue) {
        List<Map> campaignStatusList = (List) httpRequestReturnValue;
        List<CampaignStatus> campaignStatusObjectList = new ArrayList<>();

        for (int i = 0; i < campaignStatusList.size(); i++) {
            Map campaignStatus = campaignStatusList.get(i);
            campaignStatus.put("enemy", i);
            campaignStatusObjectList.add(new CampaignStatus(campaignStatus));
        }

        return campaignStatusObjectList;
    }

    public static List<AttackEvent> getAttackEvents() {
        return getAttackEvents(getStatus().get("attack_events"));
    }

    public static List<AttackEvent> getAttackEvents(Object httpRequestReturnValue) {
        Object returnValue = httpRequestReturnValue;

        if (returnValue == null) {
            return null;
        }
        Logging.log(httpRequestReturnValue.toString(), "getAttackEvents");
        List<AttackEvent> attackEventsObjectList = new ArrayList<>();
        if (returnValue instanceof Map) {
            attackEventsObjectList.add(new AttackEvent((Map) returnValue));
        } else {
            List<Map> attackEventsList = (List) returnValue;

            attackEventsList.forEach(attackEvent -> {
                attackEventsObjectList.add(new AttackEvent(attackEvent));
            });
        }

        return attackEventsObjectList;
    }

    public static List<DefendEvent> getDefendEvents() {
        return getDefendEvents(getStatus().get("defend_event"));
    }

    public static List<DefendEvent> getDefendEvents(Object httpRequestReturnValue) {
        Object returnValue = httpRequestReturnValue;

        if (returnValue == null) {
            return null;
        }

        List<DefendEvent> defendEventsObjectList = new ArrayList<>();
        if (returnValue instanceof Map) {
            defendEventsObjectList.add(new DefendEvent((Map) returnValue));
        } else {
            List<Map> defendEventsList = (List) returnValue;

            defendEventsList.forEach(defendEvent -> {
                defendEventsObjectList.add(new DefendEvent(defendEvent));
            });
        }
        return defendEventsObjectList;
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