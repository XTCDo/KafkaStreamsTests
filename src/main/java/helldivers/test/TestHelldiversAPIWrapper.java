package helldivers.test;

import com.google.gson.internal.LinkedTreeMap;
import helldivers.CampaignStatus;
import helldivers.HelldiversAPIWrapper;
import util.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";
        List<CampaignStatus> campaignStatusList = HelldiversAPIWrapper.getCampaignStatus();
        campaignStatusList.forEach(status -> {
            Logging.log(String.valueOf(status.getPoints()), TAG);
        });
    }
}
