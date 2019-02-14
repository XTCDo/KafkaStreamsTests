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

        try {
            Map attackEvents = HelldiversAPIWrapper.getAttackEvents();
            Logging.debug(attackEvents.toString(), TAG);
        } catch (Exception e){
            Logging.warn("No Attack Events at the moment");
            Logging.error(e, TAG);
        }

        try {
            Map attackEvents = HelldiversAPIWrapper.getDefendEvents();
            Logging.debug(attackEvents.toString(), TAG);
        } catch (Exception e){
            Logging.warn("No Defend Events at the moment");
            Logging.error(e, TAG);
        }


    }
}
