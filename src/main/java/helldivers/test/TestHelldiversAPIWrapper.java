package helldivers.test;

import com.google.gson.internal.LinkedTreeMap;
import helldivers.HelldiversAPIWrapper;
import util.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";
        List campaignStatus = HelldiversAPIWrapper.getCampaignStatus();
        campaignStatus.forEach(value -> Logging.log(value.toString(), TAG));
        campaignStatus.forEach(value -> Logging.log(value.getClass().toString(), TAG));
    }
}
