package helldivers;

import com.google.gson.Gson;
import util.Logging;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HelldiversAPIWrapper {
    private static final String TAG = "HelldiversAPIWrapper";

    // Link to API
    // https://docs.google.com/document/d/11BH152Tx7YpWOlfT69Ad2anG8wwlt6xOE3VO_YHC2mQ/edit#heading=h.trtt0zalsa2

    /**
     * Returns a JSON String containing the response received when performing a POST request to the
     * Helldivers api server with as sole parameter action with action being get_campaign_status
     * This JSON string contains the current status of the Helldivers campaign
     *
     * @return A JSON String containing the current status of the Helldivers campaign
     */
    public static String getAPIResponse() {
        try {
            // Create URL object from helldivers api url string
            URL url = new URL("https://api.helldiversgame.com/1.0/");

            // Create a HttpsURLConnection object using new URL object
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); // We are using POST methods

            // This has to be set to true in order to actually send a request body
            // (not all requests have a request body, but ours does)
            connection.setDoOutput(true);

            // Create a DataOutPutStream Object from the OutputStream we are getting from
            // the connection
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            // Create a StringBuilder to create a parameter String
            // also use URLEncoder to properly encode 'action' and 'get_campaign_status' to UTF-8
            StringBuilder parameterStringBuilder = new StringBuilder();
            parameterStringBuilder.append(URLEncoder.encode("action", "UTF-8"))
                .append("=")
                .append(URLEncoder.encode("get_campaign_status", "UTF-8"));

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
            StringBuffer buffer = new StringBuffer();
            // Fill the buffer with the content from the inputstream
            while ((inputLine = inputStream.readLine()) != null) {
                buffer.append(inputLine);
            }
            // Close the inputStream because we don't need it anymore
            inputStream.close();

            return buffer.toString();
        } catch (Exception e) {
            Logging.error(e, TAG);
            return null;
        }
    }

    /**
     * Fetches the API response, and then uses gson to parse the JSON string to a Map
     * @return A Map containing the current (full) status of the Helldivers campaign
     */
    public static Map getStatus() {
        try {
            // Get JSON String from the api response
            String apiResponseJSONString = getAPIResponse();

            // parse with Gson and return the resulting map
            Gson gson = new Gson();
            return gson.fromJson(apiResponseJSONString, Map.class);

        } catch (Exception e) {
            Logging.error(e, TAG);
            return null;
        }
    }

    public static List<Statistics> getStatistics(Map fullStatus) {
        List<Map> mapList = getSubmapWithTimestamp(fullStatus,"statistics");
        if (mapList == null) return null;

        // Make an empty List of Statistics
        List<Statistics> statisticsList = new ArrayList<>();

        // Iterate through the Maps in statisticsList, use those to create a new statistics Object
        // and store them in statisticsObjectList
        mapList.forEach(statistics -> statisticsList.add(new Statistics(statistics)));

        // return the list of parsed objects
        return statisticsList;
    }

    public static List<Statistics> getStatistics() { return getStatistics(getStatus()); }

    /**
     * extract
     * @param fullStatus full HTTP API response, parsed to java map
     * @return a list of Objects representing the Campaign status for all three enemy factions
     */
    public static List<CampaignStatus> getCampaignStatus(Map fullStatus) {
        List<Map> mapList = getSubmapWithTimestamp(fullStatus,"campaign_status");
        if (mapList == null) return null;

        // Make an empty List of CampaignStatus objects
        List<CampaignStatus> campaignStatusList = new ArrayList<>();

        // Iterate through the Maps in mapList, add their relative index
        // and then use that info to create a new Campaign status object
        for (int i = 0; i < mapList.size(); i++) {
            Map campaignStatusMap = mapList.get(i);
            campaignStatusMap.put("enemy", i); // insert enemy index
            campaignStatusList.add(new CampaignStatus(campaignStatusMap));
        }

        return campaignStatusList;
    }

    public static List<CampaignStatus> getCampaignStatus() {
        return getCampaignStatus(getStatus());
    }

    public static List<AttackEvent> getAttackEvents(Map fullStatus) {
        List<Map> mapList = getSubmapWithTimestamp(fullStatus, "attack_events");
        if (mapList == null) return null;

        // Make an empty List of AttackEvents
        List<AttackEvent> attackEventsList = new ArrayList<>();

        // convert list of Maps to List of AttackEvents
        mapList.forEach(attackEventMap -> attackEventsList.add( new AttackEvent(attackEventMap) ) );

        return attackEventsList;
    }

    public static List<AttackEvent> getAttackEvents() {
        return getAttackEvents(getStatus());
    }

    public static List<DefendEvent> getDefendEvents(Map fullStatus) {
        List<Map> mapList = getSubmapWithTimestamp(fullStatus, "defend_event");
        if (mapList == null) return null;
        // Make an empty List of DefendEvents
        List<DefendEvent> defendEventsList = new ArrayList<>();
        // populate the List of DefendEvents
        mapList.forEach(defendEventMap -> defendEventsList.add( new DefendEvent(defendEventMap) ) );

        return defendEventsList;
    }

    public static List<DefendEvent> getDefendEvents() {
        return getDefendEvents(getStatus());
    }

    /**
     * utility function that extracts the specified sub map from the root map and adds a timeStamp
     * @param input root map
     * @param key key of sub-map
     * @return a Map object representing the sub map of the root map with an additional timeStamp
     */
    private static List<Map> getSubmapWithTimestamp(Map input, String key) {
        try {
            long timeStamp = Math.round((double) input.get("time")); //extract timestamp
            Object inputObject = input.get(key); // fetch raw sub-map

            // parse to list
            List<Map> mapList = (inputObject instanceof Map) ? Collections.singletonList( (Map) inputObject)
                    : ( inputObject instanceof List) ?
                            ( ( (List) inputObject).get(0) instanceof Map)?
                                    (List<Map>) inputObject
                                    : null
                            : null;

            mapList.forEach(map -> map.put("timeStamp", timeStamp));

            return mapList;
        } catch (NullPointerException npe) {
            return null;
        }
    }
}
