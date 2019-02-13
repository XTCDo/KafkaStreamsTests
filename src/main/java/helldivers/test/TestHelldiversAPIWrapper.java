package helldivers.test;

import helldivers.HelldiversAPIWrapper;
import util.Logging;

import java.util.ArrayList;
import java.util.Map;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";
        Map response = (Map) HelldiversAPIWrapper.doHTTPRequest("get_status");
        ArrayList campaignStatus = (ArrayList) response.get("campaign_status");
        ArrayList defendEvents = (ArrayList)  response.get("defend_event");
        ArrayList attackEvents = (ArrayList) response.get("attack_event");

        campaignStatus.forEach(faction -> Logging.log(faction.toString(), TAG));
        defendEvents.forEach(event -> Logging.log(event.toString(), TAG));
        attackEvents.forEach(event -> Logging.log(event.toString(), TAG));
    }
}
