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

    /**
     * Returns a JSON String containing the response received when performing a POST request to the
     * Helldivers api server with as sole parameter action with action being get_campaign_status
     * This JSON string containts the current status of the Helldivers campaign
     *
     * @return A JSON String containing the current status of the Helldivers campaign
     */
    public static String getAPIResponse() {
        final String TAG = "HelldiversAPIWrapper";
        try {
            // Create URL object from helldivers api url string
            URL url = new URL("https://api.helldiversgame.com/1.0/");

            // Create a HttpsURLConnection object using new URL object
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); // We are using POST methods


            StringBuilder parameterStringBuilder = new StringBuilder();
            parameterStringBuilder.append(URLEncoder.encode("action", "UTF-8"))
                .append("=")
                .append(URLEncoder.encode("get_campaign_status", "UTF-8"));

            // This has to be set to true in order to actually send a request body
            // (not all requests have a request body, but ours does)
            connection.setDoOutput(true);

            // Create a DataOutPutStream Object from the OutputStream we are getting from
            // the connection
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            // Write the parameters to the outputStream using the ParameterStringBuilder
            // helper class
            outputStream.writeBytes(parameterStringBuilder.toString());

            // Flush the outputStream to our destination
            outputStream.flush();

            // Close the outputStream because it is no longer needed
            outputStream.close();

            // Use BufferedReader to read the result from our request
            BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(
                    connection.getInputStream())); // Use inputStream from connection

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

    /**
     * Returns a Map that is created by parsing the JSON String returned by getAPIResponse() using
     * gson This Map contains the current status of the Helldivers campaign
     *
     * @return A Map containing the current status of the Helldivers campaign
     */
    public static Map getStatus() {
        final String TAG = "HelldiversAPIWrapper";
        try {
            // Get JSON String from the api response
            String apiResponseJSONString = getAPIResponse();

            // Create a Map out of it using gson
            Gson gson = new Gson();
            Map apiResponse = gson.fromJson(apiResponseJSONString, Map.class);

            return apiResponse;
        } catch (Exception e) {
            Logging.error(e, TAG);
            return null;
        }
    }

    // todo change Object to List if that doesn't break anything
    // todo Make the cooperation between getSomething(Object httpRequestReturnValue) and
    // todo getSomething() more clear and logical
    public static List<Statistics> getStatistics(Object httpRequestReturnValue) {
        // Turn httpRequestReturnValue into a List of Maps
        List<Map> statisticsList = (List) httpRequestReturnValue;

        // Make an empty List of Statistics
        List<Statistics> statisticsObjectList = new ArrayList<>();

        // Iterate through the Maps in statisticsList, turn them into a Statistics Object
        // and store them in statisticsObjectList
        statisticsList.forEach(statistics -> {
            statisticsObjectList.add(new Statistics(statistics));
        });

        return statisticsObjectList;
    }

    public static List<Statistics> getStatistics() {
        return getStatistics(getStatus().get("statistics"));
    }

    public static List<CampaignStatus> getCampaignStatus(Object httpRequestReturnValue) {
        // Turn httpRequestReturnValue into a List of Maps
        List<Map> campaignStatusList = (List) httpRequestReturnValue;

        // Make an empty List of CampaignStatuses
        List<CampaignStatus> campaignStatusObjectList = new ArrayList<>();

        // Iterate through the Maps in campaignStatusList, add the index indicating which enemy the
        // CampaignStatus Object is relevant to to the map and then add it to
        // campaignStatusObjectList. The reason this is a for int loop and not a for each is that
        // the enemy factions are always introduced in the same order and thus the index in the list
        // determines the faction
        for (int i = 0; i < campaignStatusList.size(); i++) {
            Map campaignStatus = campaignStatusList.get(i);
            campaignStatus.put("enemy", i);
            campaignStatusObjectList.add(new CampaignStatus(campaignStatus));
        }

        return campaignStatusObjectList;
    }

    public static List<CampaignStatus> getCampaignStatus() {
        return getCampaignStatus(getStatus().get("campaign_status"));
    }

    public static List<AttackEvent> getAttackEvents(Object httpRequestReturnValue) {
        // This is probably unnecessary
        // todo remove this and make sure everything still works
        Object returnValue = httpRequestReturnValue;

        // Check if returnValue is empty, if is: return null
        if (returnValue == null) {
            return null;
        }
        Logging.log(httpRequestReturnValue.toString(), "getAttackEvents");

        // Make an empty List of AttackEvents
        List<AttackEvent> attackEventsObjectList = new ArrayList<>();

        // If returnValue is a map, that means only a single AttackEvent is present,
        // if that's the case, create that single AttackEvent and add it to the List
        // Otherwise just iterate through the list, turn every map into an AttackEvent
        // and add that AttackEvent to attackEventsObjectList
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

    public static List<AttackEvent> getAttackEvents() {
        return getAttackEvents(getStatus().get("attack_events"));
    }

    public static List<DefendEvent> getDefendEvents(Object httpRequestReturnValue) {
        // This is probably unnecessary
        // todo remove this and make sure everything still works
        Object returnValue = httpRequestReturnValue;

        // Check if returnValue is empty, if it is: return null
        if (returnValue == null) {
            return null;
        }

        // Make an empty List of AttackEvents
        List<DefendEvent> defendEventsObjectList = new ArrayList<>();

        // If returnValue is a map, that means only a single DefendEvent is present,
        // if that's the case, create that single DefendEvent and add it to the List
        // Otherwise just iterate through the list, turn every map into a DefendEvent
        // and add that DefendEvent to defendEventObjectList
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

    public static List<DefendEvent> getDefendEvents() {
        return getDefendEvents(getStatus().get("defend_event"));
    }

}
