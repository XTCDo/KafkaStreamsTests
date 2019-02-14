package helldivers.test;

import helldivers.*;
import org.apache.kafka.common.metrics.Stat;
import sun.rmi.runtime.Log;
import util.Logging;

import java.util.List;
import java.util.logging.Level;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";

        try {
            List<AttackEvent> attackEvents = HelldiversAPIWrapper.getAttackEvents();
            attackEvents.forEach(attackEvent -> Logging.log(attackEvent.getDescription(), TAG));
        } catch (Exception e){
            Logging.warn("No Attack Events at the moment", TAG);
            Logging.error(e, TAG);
        }

        try {
            List<DefendEvent> defendEvents = HelldiversAPIWrapper.getDefendEvents();
            defendEvents.forEach(defendEvent -> Logging.log(defendEvent.getDescription(), TAG));
        } catch (Exception e){
            Logging.warn("No Defend Events at the moment", TAG);
            Logging.error(e, TAG);
        }


        try {
            List<Statistics> statistics = HelldiversAPIWrapper.getStatistics();
            statistics.forEach(stats -> Logging.log(stats.getDescription(), TAG));
        } catch (Exception e){
            Logging.error(e, TAG);
        }

        Status status = new Status();
        List<CampaignStatus> campaignStatusList = status.getCampaignStatuses();
        List<AttackEvent> attackEventList = status.getAttackEvents();
        List<DefendEvent> defendEventList = status.getDefendEvents();
        List<Statistics> statisticsList = status.getStatistics();

        try {
            campaignStatusList.forEach(campaignStatus -> Logging.log("\n" + campaignStatus.toString(), TAG));
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
            defendEventList.forEach(defendEvent -> Logging.log(defendEvent.getDescription(), TAG));
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
