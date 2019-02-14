package helldivers;

import java.util.List;
import java.util.Map;

public class Status {
    private List<CampaignStatus> campaignStatuses;
    private List<DefendEvent> defendEvents;
    private List<AttackEvent> attackEvents;
    private List<Statistics> statistics;

    public Status(){
        Map httpApiResponseObject = HelldiversAPIWrapper.getStatus();
        this.campaignStatuses = HelldiversAPIWrapper.getCampaignStatus(httpApiResponseObject.get("status"));
        this.defendEvents = HelldiversAPIWrapper.getDefendEvents(httpApiResponseObject.get("defend_event"));
        this.attackEvents = HelldiversAPIWrapper.getAttackEvents(httpApiResponseObject.get("attack_event"));
        this.statistics = HelldiversAPIWrapper.getStatistics(httpApiResponseObject.get("statistics"));
    }
}
