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
        campaignStatus.forEach(faction -> Logging.log(faction.toString(), TAG));
    }
}
