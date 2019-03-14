package helldivers;

import java.util.List;
import java.util.Map;

public class Status {
    private Map httpApiResponseObject;
    private List<CampaignStatus> campaignStatuses;
    private List<DefendEvent> defendEvents;
    private List<AttackEvent> attackEvents;
    private List<Statistics> statistics;

    public Status(){
        refresh();
    }

    public Status(Map fullStatus){
        this.httpApiResponseObject = fullStatus;
        this.campaignStatuses = HelldiversAPIWrapper.getCampaignStatus(httpApiResponseObject.get("campaign_status"));
        this.defendEvents = HelldiversAPIWrapper.getDefendEvents(httpApiResponseObject.get("defend_event"));//potentially broken, check topic helldivers-status
        this.attackEvents = HelldiversAPIWrapper.getAttackEvents(httpApiResponseObject.get("attack_event"));// same as above
        this.statistics = HelldiversAPIWrapper.getStatistics(httpApiResponseObject.get("statistics"));
    }

    public void refresh(){
        this.httpApiResponseObject = HelldiversAPIWrapper.getStatus();
        this.campaignStatuses = HelldiversAPIWrapper.getCampaignStatus(httpApiResponseObject.get("campaign_status"));
        this.defendEvents = HelldiversAPIWrapper.getDefendEvents(httpApiResponseObject.get("defend_event"));
        this.attackEvents = HelldiversAPIWrapper.getAttackEvents(httpApiResponseObject.get("attack_event"));
        this.statistics = HelldiversAPIWrapper.getStatistics(httpApiResponseObject.get("statistics"));
    }

    public Map getHttpApiResponseObject(){
        return httpApiResponseObject;
    }

    public List<CampaignStatus> getCampaignStatuses() {
        return campaignStatuses;
    }

    public List<DefendEvent> getDefendEvents() {
        return defendEvents;
    }

    public List<AttackEvent> getAttackEvents() {
        return attackEvents;
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }

}
