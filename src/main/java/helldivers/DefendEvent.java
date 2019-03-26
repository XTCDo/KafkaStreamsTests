package helldivers;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Point;
import util.MapUtils;

public class DefendEvent {
    /**
     * Unix timestamp of measurement
     */
    private final long timeStamp;
    /**
     * Season/war number
     */
    private final int season;

    /**
     * Defence event id, probably equal to the total amount of defense events minus 1
     */
    private final int eventId;

    /**
     * UNIX Timestamp of when the attack event started
     */
    private final long startTime;

    /**
     * UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     */
    private final long endTime;

    /**
     * Id of the region in which the planet that is being attacked lies
     */
    private final int region;

    /**
     * Id of the enemy on the attacked planet
     */
    private final int enemy;

    /**
     * Current amount of points that the players have gained
     */
    private final int points;

    /**
     * Amount of points needed for the attack event to be successful
     */
    private final int pointsMax;

    /**
     * Either 'active', 'success' or 'failure depending on if the event is ongoing,
     * successfully ended or ended in a loss
     */
    private final String status;

    /**
     * Constructor for DefendEvent that takes a map containing values returned by the helldivers API
     * @param values a map representing this object
     */
    public DefendEvent(Map values){
        // todo change these, they will only be called on construction, no longer from gson
        this(
            (long) values.get("timeStamp"),
            (int) Math.round((double) values.get("season")),
            (int) Math.round((double) MapUtils.safeGet(values, "event_id")),
            (long) Math.round((double) MapUtils.safeGet(values, "start_time")),
            (long) Math.round((double) MapUtils.safeGet(values, "end_time")),
            (int) Math.round((double) values.get("region")),
            (int) Math.round((double) values.get("enemy")),
            (int) Math.round((double) values.get("points")),
            (int) Math.round((double) MapUtils.safeGet(values, "points_max")),
            (String) values.get("status")
        );
    }

    /**
     * Regular constructor for DefendEvent
     * @param timeStamp the timestamp for when this event was polled from the database
     * @param season Season/war number
     * @param eventId Attack event id, presumed to be equal to the total amount of attack events minus 1
     * @param startTime UNIX Timestamp of when the attack event started
     * @param endTime UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     * @param region Id of the region in which the planet that is being attacked lies
     * @param enemy Current amount of points that the players have gained
     * @param points Current amount of points that the players have gained
     * @param pointsMax Amount of points needed for the attack event to be successful
     * @param status Either 'active', 'success' or 'failure' depending on if the event is ongoing,
     *               successfully ended or ended in a loss
     */
    public DefendEvent(long timeStamp,int season, int eventId, long startTime, long endTime, int region,
                       int enemy, int points, int pointsMax, String status){
        this.timeStamp = timeStamp;
        this.season = season;
        this.eventId = eventId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.region = region;
        this.enemy = enemy;
        this.points = points;
        this.pointsMax = pointsMax;
        this.status = status;
    }

    public long getTimeStamp() {return timeStamp;}

    /**
     * Season/war number
     */
    public int getSeason() { return season; }

    /**
     * Returns a String describing the DefendEvent
     * @return a String describing the DefendEvent
     */
    public String getDescription(){
        StringBuilder description = new StringBuilder();
        description.append(this.toString()).append("\n")
                .append("Enemy:      ").append(getEnemyName()).append("\n")
                .append("Status:     ").append(getStatus()).append("\n")
                .append("Start time: ").append(new Date(getStartTime()*1000)).append("\n")
                .append("End time:   ").append(new Date(getEndTime()*1000)).append("\n")
                .append("Region      ").append(getRegion()).append("\n")
                .append("Points:     ").append(getPoints()).append("\n")
                .append("Points Max: ").append(getPointsMax()).append("\n")
                .append("Event id:   ").append(getEventId())
                .append("\n");
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
                .tag("event_id", String.valueOf(getEventId()))
                .tag("status", getStatus())
                .addField("region", getRegion())
                .addField("start_time", getStartTime())
                .addField("end_time", getEndTime())
                .addField("points", getPoints())
                .addField("points_max", getPointsMax())
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
     * Defence event id, probably equal to the total amount of defense events minus 1
     */
    public int getEventId() { return eventId; }

    /**
     * UNIX Timestamp of when the attack event started
     */
    public long getStartTime() { return startTime; }

    /**
     * UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     */
    public long getEndTime() { return endTime; }

    /**
     * Id of the region in which the planet that is being attacked lies
     */
    public int getRegion() { return region; }

    /**
     * Id of the enemy on the attacked planet
     */
    public int getEnemy() { return enemy; }

    /**
     * Current amount of points that the players have gained
     */
    public int getPoints() { return points; }


    /**
     * Amount of points needed for the attack event to be successful
     */
    public int getPointsMax() { return pointsMax; }

    /**
     * Either 'active', 'success' or 'failure depending on if the event is ongoing,
     * succesfully ended or ended in a loss
     */
    public String getStatus() { return status; }

}
