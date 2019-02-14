package helldivers.test;

import com.google.gson.internal.LinkedTreeMap;
import helldivers.*;
import sun.rmi.runtime.Log;
import util.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
        List<CampaignStatus> campaignStatusList = status.getCampaignStatuses();
        List<AttackEvent> attackEventList = status.getAttackEvents();
        List<DefendEvent> defendEventList = status.getDefendEvents();
        List<Statistics> statisticsList = status.getStatistics();

        try {
            campaignStatusList.forEach(campaignStatus -> Logging.log(campaignStatus.toString(), TAG));
        } catch (NullPointerException e) {
            Logging.error(e, TAG);
            Logging.log(Level.SEVERE,"No CampaignStatuses", TAG);
        }

        try {
            attackEventList.forEach(attackEvent -> Logging.log(attackEvent.toString(), TAG));
        } catch (NullPointerException e) {
            Logging.error(e, TAG);
            Logging.log(Level.SEVERE,"No AttackEvents", TAG);
        }

        try {
            defendEventList.forEach(defendEvent -> Logging.log(defendEvent.toString(), TAG));
        } catch (NullPointerException e) {
            Logging.error(e, TAG);
            Logging.log(Level.SEVERE,"No DefendEvents", TAG);
        }

        try {
            statisticsList.forEach(statistics -> Logging.log(statistics.toString(), TAG));
        } catch (NullPointerException e) {
            Logging.error(e, TAG);
            Logging.log(Level.SEVERE,"No Statistics", TAG);
        }
    }
}
