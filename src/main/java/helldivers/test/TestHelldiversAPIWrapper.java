package helldivers.test;

import com.google.gson.internal.LinkedTreeMap;
import helldivers.HelldiversAPIWrapper;
import util.Logging;

import java.util.ArrayList;
import java.util.Map;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";
        Logging.log(HelldiversAPIWrapper.getCampaignStatus().toString(), TAG);
        //Logging.log(HelldiversAPIWrapper.getAttackEvents().toString(), TAG);
        Logging.log(HelldiversAPIWrapper.getDefendEvents().toString(), TAG);
    }
}
