package helldivers.test;

import helldivers.*;
import util.Logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";

        Logging.log("Tests using new Status()", TAG);

        Logging.log(HelldiversAPIWrapper.getAPIResponse(), TAG);

        Status status = new Status();
        List<CampaignStatus> campaignStatusList = status.getCampaignStatuses();
        List<AttackEvent> attackEventList = status.getAttackEvents();
        List<DefendEvent> defendEventList = status.getDefendEvents();
        List<Statistics> statisticsList = status.getStatistics();

        try {
            campaignStatusList.forEach(campaignStatus -> Logging.log(campaignStatus.getDescription(), TAG));
        } catch (NullPointerException e) {
            Logging.error(e, TAG);
            Logging.log(Level.SEVERE,"No CampaignStatuses", TAG);
        }

        try {
            attackEventList.forEach(attackEvent -> Logging.log(attackEvent.getDescription(), TAG));
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
            statisticsList.forEach(statistics -> Logging.log(statistics.getDescription(), TAG));
        } catch (NullPointerException e) {
            Logging.error(e, TAG);
            Logging.log(Level.SEVERE,"No Statistics", TAG);
        }

        Logging.log("Tests for fixing weird attackevents behaviour", TAG);

        List<CampaignStatus> campaignStatusListFromWrapper =
            HelldiversAPIWrapper.getCampaignStatus();
        List<Statistics> statisticsListFromWrapper =
            HelldiversAPIWrapper.getStatistics();
        List<AttackEvent> attackEventsListFromWrapper =
            HelldiversAPIWrapper.getAttackEvents();
        List<DefendEvent> defendEventsListFromWrapper =
            HelldiversAPIWrapper.getDefendEvents();

        Logging.log(campaignStatusListFromWrapper.toString(), TAG);
        Logging.log(statisticsListFromWrapper.toString(), TAG);
        Logging.log(attackEventsListFromWrapper.toString(), TAG);
        Logging.log(defendEventsListFromWrapper.toString(), TAG);
    }
}
