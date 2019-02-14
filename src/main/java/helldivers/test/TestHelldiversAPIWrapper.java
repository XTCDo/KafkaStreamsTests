package helldivers.test;

import com.google.gson.internal.LinkedTreeMap;
import helldivers.CampaignStatus;
import helldivers.HelldiversAPIWrapper;
import helldivers.Status;
import sun.rmi.runtime.Log;
import util.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";


        try {
            List attackEvents = HelldiversAPIWrapper.getAttackEvents();
            Logging.debug(attackEvents.toString(), TAG);
        } catch (Exception e){
            Logging.warn("No Attack Events at the moment", TAG);
            Logging.error(e, TAG);
        }

        try {
            List defendEvents = HelldiversAPIWrapper.getDefendEvents();
            Logging.debug(defendEvents.toString(), TAG);
            Logging.log(defendEvents.toString(), TAG);
        } catch (Exception e){
            Logging.warn("No Defend Events at the moment", TAG);
            Logging.error(e, TAG);
        }


        try {
            HelldiversAPIWrapper.getStatistics().forEach(stats -> Logging.log(stats.toString(), TAG));
        } catch (Exception e){
            Logging.error(e, TAG);
        }

        Status status = new Status();

        status.getCampaignStatuses().forEach(campaignStatus -> Logging.log(campaignStatus.toString(), TAG));
        status.getAttackEvents().forEach(attackEvent -> Logging.log(attackEvent.toString(), TAG));
        status.getDefendEvents().forEach(defendEvent -> Logging.log(defendEvent.toString(), TAG));
        status.getStatistics().forEach(statistics -> Logging.log(statistics.toString(), TAG));
    }
}
