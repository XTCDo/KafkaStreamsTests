package helldivers;

import org.influxdb.dto.Point;
import util.MapUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CampaignStatus {

    /**
     * timestamp of when this status was polled from the helldivers database.
     */
    private final long timeStamp;

    /**
     * Season/War number
     */
    private final int season;

    /**
     * The id of the enemy this CampaignStatus belongs to
     */
    private final int enemy;

    /**
     * Current amount of influence points gained by players
     */
    private final int points;

    /**
     * Total amount of influence points gained by players
     */
    private final int pointsTaken;

    /**
     * Required amount of influence points in order to start a home planet assault event/attack event
     */
    private final int pointsMax;

    /**
     * Either 'active' if there are missions available, 'defeated' if the faction has been defeated or
     * 'hidden' if war has not yet been declared.
     */
    private final String status;

    /**
     * Order in which the faction was introduced to the war, 255 if the faction hasn't been introduced yet
     */
    private final int introductionOrder;

    /**
     * Constructor for CampaignStatus that takes a Map containing values returned by the helldivers API
     * @param values Map containing values returned by the helldivers API
     */
    public CampaignStatus(Map values){
        this(
            values.get("timeStamp"),
            (int) Math.round((double) values.get("season")),
            (int) values.get("enemy"),
            (int) Math.round((double) MapUtils.safeGet(values, "points")),
            (int) Math.round((double) MapUtils.safeGet(values, "points_taken")),
            (int) Math.round((double) MapUtils.safeGet(values, "points_max")),
            (String) values.get("status"),
            (int) Math.round((double) MapUtils.safeGet(values, "introduction_order"))
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
    public CampaignStatus(Object timeStamp, int season, int enemy,
                          int points, int pointsTaken, int pointsMax,
                          String status, int introductionOrder){
        this.timeStamp = (timeStamp instanceof Long) ? (long) timeStamp : Math.round( (double) timeStamp);
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
                .append("Status:             ").append(getStatus()).append("\n")
                .append("Introduction order: ").append(getIntroductionOrder()).append("\n");
        return description.toString();
    }

    /**
     * cast this object to an influx point
     * @param table name of the measurement
     * @return influx Point representing this object
     */
    public Point toPoint(String table){
        return Point.measurement(table)
                .time(timeStamp, TimeUnit.MILLISECONDS)
                .tag("season", String.valueOf(getSeason()))
                .tag("enemy", getEnemyName())
                .tag("introduction_order", String.valueOf(getIntroductionOrder()))
                .tag("status", getStatus())
                .addField("points", getPoints())
                .addField("points_taken", getPointsTaken())
                .addField("points_max",getPointsMax())
                .build();
    }
    /**
     * Returns the name of the enemy that is being attacked in this AttackEvent
     * @return the name of the enemy that is being attacked in this AttackEvent
     */
    public String getEnemyName(){
        String[] enemies = new String[] {"Bugs", "Cyborgs", "Illuminate"};
        return enemies[getEnemy()];
    }

    /**
     * Season/War number
     */
    public int getSeason() {
        return season;
    }

    /**
     * Total amount of influence points gained by players
     */
    public int getPointsTaken() {
        return pointsTaken;
    }

    /**
     * Required amount of influence points in order to start a home planet assault event/attack event
     */
    public int getPointsMax() {
        return pointsMax;
    }


    /**
     * Either 'active' if there are missions available, 'defeated' if the faction has been defeated or
     * 'hidden' if war has not yet been declared.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Order in which the faction was introduced to the war, 255 if the faction hasn't been introduced yet
     */
    public int getIntroductionOrder() {
        return introductionOrder;
    }

    /**
     * Current amount of influence points gained by players
     */
    public int getPoints() {
        return points;
    }

    /**
     * The id of the enemy this CampaignStatus belongs to
     */ /**
     * The id of the enemy this campaignstatus belongs to
     */
    public int getEnemy() {
        return enemy;
    }
    /**
     * The time at which this measurement was made
     */
    public long getTimeStamp() {
        return timeStamp;
    }
}
