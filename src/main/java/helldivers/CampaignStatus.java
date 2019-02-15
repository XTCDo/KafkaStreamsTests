package helldivers;

import java.util.Map;

public class CampaignStatus {
    /**
     * Season/War number
     */
    private int season;

    private int enemy;

    /**
     * Current amount of influence points gained by players
     */
    private int points;

    /**
     * Total amount of influence points gained by players
     */
    private int pointsTaken;

    /**
     * Required amount of influence points in order to start a home planet assault event/attack event
     */
    private int pointsMax;

    /**
     * Either 'active' if there are missions available, 'defeated' if the faction has been defeated or
     * 'hidden' if war has not yet been declared.
     */
    private String status;

    /**
     * Order in which the faction was introduced to the war, 255 if the faction hasn't been introduced yet
     */
    private int introductionOrder;

    /**
     * Constructor for CampaignStatus that takes a Map containing values returned by the helldivers API
     * @param values Map containing values returned by the helldivers API
     */
    public CampaignStatus(Map values){
        this(
            (int) Math.round((double) values.get("season")),
            (int) Math.round((double) values.get("enemy")),
            (int) Math.round((double) values.get("points")),
            (int) Math.round((double) values.get("points_taken")),
            (int) Math.round((double) values.get("points_max")),
            (String) values.get("status"),
            (int) Math.round((double) values.get("introduction_order"))
        );
    }

    /**
     * Regular constructor for CampaignStatus
     * @param season Season/War number
     * @param points Current amount of influence points gained by players
     * @param pointsTaken Required amount of influence points in order to start a home planet assault event/attack event
     * @param pointsMax Required amount of influence points in order to start a home planet assault event/attack event
     * @param status Either 'active' if there are missions available, 'defeated' if the faction has been defeated or
     *               'hidden' if war has not yet been declared.
     * @param introductionOrder Order in which the faction was introduced to the war,
     *                          255 if the faction hasn't been introduced yet
     */
    public CampaignStatus(int season, int enemy, int points, int pointsTaken, int pointsMax, String status, int introductionOrder){
        this.season = season;
        this.enemy = enemy;
        this.points = points;
        this.pointsTaken = pointsTaken;
        this.pointsMax = pointsMax;
        this.status = status;
        this.introductionOrder = introductionOrder;
    }

    /**
     * Returns a String describing the CampaignStatus
     * @return a String describing the CampaignStatus
     */
    public String getDescription(){
        StringBuilder description = new StringBuilder();
        description.append(this.toString()).append("\n")
                .append("Enemy:              ").append(getEnemyName()).append("\n")
                .append("War:                ").append(getSeason()).append("\n")
                .append("Points:             ").append(getPoints()).append("\n")
                .append("Points Max:         ").append(getPointsMax()).append("\n")
                .append("Status:             ").append(getPointsMax()).append("\n")
                .append("Introduction order: ").append(getIntroductionOrder()).append("\n");
        return description.toString();
    }


    /**
     * Returns the name of the enemy that is being attacked in this AttackEvent
     * @return the name of the enemy that is being attacked in this AttackEvent
     */
    public String getEnemyName(){
        String[] enemies = new String[] {"Bugs", "Cyborgs", "Illuminate"};
        return enemies[getEnemy()];
    }


    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getPointsTaken() {
        return pointsTaken;
    }

    public void setPointsTaken(int pointsTaken) {
        this.pointsTaken = pointsTaken;
    }

    public int getPointsMax() {
        return pointsMax;
    }

    public void setPointsMax(int pointsMax) {
        this.pointsMax = pointsMax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIntroductionOrder() {
        return introductionOrder;
    }

    public void setIntroductionOrder(int introductionOrder) {
        this.introductionOrder = introductionOrder;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * The id of the enemy this campaignstatus belongs to
     */
    public int getEnemy() {
        return enemy;
    }

    public void setEnemy(int enemy) {
        this.enemy = enemy;
    }
}
