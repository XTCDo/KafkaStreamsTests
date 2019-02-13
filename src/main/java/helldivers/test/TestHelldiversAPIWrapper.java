package helldivers.test;

import com.google.gson.internal.LinkedTreeMap;
import helldivers.HelldiversAPIWrapper;
import util.Logging;

import java.util.ArrayList;
import java.util.Map;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";
        Map response = (Map) HelldiversAPIWrapper.doHTTPRequest("get_status");
        ArrayList campaignStatus = (ArrayList) response.get("campaign_status");
        LinkedTreeMap defendEvents = (LinkedTreeMap)  response.get("defend_event");

        Logging.log(response.get("defend_event").toString());
    }
}
