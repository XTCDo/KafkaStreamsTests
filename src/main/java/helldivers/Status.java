package helldivers;

import java.util.List;
import java.util.Map;

public class Status {
    private Map httpApiResponseObject;
    private List<CampaignStatus> campaignStatuses;
    private List<DefendEvent> defendEvents;
    private List<AttackEvent> attackEvents;
    private List<Statistics> statistics;

    public Status() { refresh();
    }

    public Status(Map fullStatus) { setAll(fullStatus); }

    public void refresh() { setAll(HelldiversAPIWrapper.getStatus()); }

    // getters

    public Map getHttpApiResponseObject() { return httpApiResponseObject; }

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

    // setters

    private void setAll(Map fullStatus){
        this.httpApiResponseObject = fullStatus;
        this.campaignStatuses = HelldiversAPIWrapper.getCampaignStatus(httpApiResponseObject);
        this.defendEvents = HelldiversAPIWrapper.getDefendEvents(httpApiResponseObject);//potentially broken, check topic helldivers-status
        this.attackEvents = HelldiversAPIWrapper.getAttackEvents(httpApiResponseObject);// same as above
        this.statistics = HelldiversAPIWrapper.getStatistics(httpApiResponseObject);
    }
}
