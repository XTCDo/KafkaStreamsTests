package helldivers;

import java.sql.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Point;
import util.MapUtils;

public class AttackEvent {
    /**
     * UNIX Timestamp of when the attack event was polled from the database
     */
    private final long timeStamp;

    /**
     * Season/war number
     */
    private final int season;

    /**
     * Attack event id, presumed to be equal to the total amount of attack events minus 1
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
     * The amount of players that are in a mission in the region where the attack event starts at the time
     * the attack event started
     */
    private final int playersAtStart;

    /**
     * Unsure what this value is
     */
    private final int maxEventId;

    /**
     * Constructor for AttackEvent that takes a Map containing values returned by the
     * helldivers API
     * @param values Map containing values returned by the helldivers API
     */
    public AttackEvent(Map values){
        this(
                (long) Math.round((double) values.get("timeStamp")),
                (int) Math.round((double) values.get("season")),
                (int) Math.round((double) MapUtils.safeGet(values, "event_id")),
                (long) Math.round((double) MapUtils.safeGet(values, "start_time")),
                (long) Math.round((double) MapUtils.safeGet(values, "end_time")),
                (int) Math.round((double) values.get("enemy")),
                (int) Math.round((double) values.get("points")),
                (int) Math.round((double) MapUtils.safeGet(values, "points_max")),
                (String) values.get("status"),
                (int) Math.round((double) MapUtils.safeGet(values, "players_at_start")),
                (int) Math.round((double) MapUtils.safeGet(values, "max_event_id"))
        );
    }

    /**
     * Regular constructor for AttackEvent
     * @param season Season/war number
     * @param eventId Attack event id, presumed to be equal to the total amount of attack events minus 1
     * @param startTime UNIX Timestamp of when the attack event started
     * @param endTime UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     * @param enemy Current amount of points that the players have gained
     * @param points Current amount of points that the players have gained
     * @param pointsMax Amount of points needed for the attack event to be successful
     * @param status Either 'active', 'success' or 'failure depending on if the event is ongoing,
     *               succesfully ended or ended in a loss
     * @param playersAtStart The amount of players that are in a mission in the region where the attack event starts at
     *                       the time the attack event started
     * @param maxEventId Unsure what this value is
     */
    public AttackEvent(long timeStamp, int season, int eventId, long startTime, long endTime, int enemy, int points, int pointsMax,
                       String status, int playersAtStart, int maxEventId){
        this.timeStamp = timeStamp;
        this.season = season;
        this.eventId = eventId;
        this.endTime = endTime;
        this.startTime = startTime;
        this.enemy = enemy;
        this.points = points;
        this.pointsMax = pointsMax;
        this.status = status;
        this.playersAtStart = playersAtStart;
        this.maxEventId = maxEventId;
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
                .tag("max_event_id", String.valueOf(getMaxEventId()))
                .tag("status", getStatus())
                .addField("start_time", getStartTime())
                .addField("end_time", getEndTime())
                .addField("players_at_start", getPlayersAtStart())
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
     * Returns a String describing the AttackEvent
     * @return a String describing the AttackEvent
     */
    public String getDescription(){
        StringBuilder description = new StringBuilder();
        description.append(this.toString()).append("\n")
                .append("Enemy:            ").append(getEnemyName()).append("\n")
                .append("Status:           ").append(getStatus()).append("\n")
                .append("Start time:       ").append(new Date(getStartTime()*1000)).append("\n")
                .append("End time:         ").append(new Date(getEndTime()*1000)).append("\n")
                .append("Points:           ").append(getPoints()).append("\n")
                .append("PointsMax:        ").append(getPointsMax()).append("\n")
                .append("Players At Start: ").append(getPlayersAtStart()).append("\n")
                .append("Event id:         ").append(getEventId()).append("\n")
                .append("Max Event id:     ").append(getMaxEventId()).append("\n");
        return description.toString();
    }

    /**
     * @return timestamp that describes the moment at which the database was polled for this data
     */
    public long getTimeStamp(){ return timeStamp;}

    /**
     * @return Attack event id, presumed to be equal to the total amount of attack events minus 1
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * UNIX Timestamp of when the attack event started
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Id of the enemy on the attacked planet
     */
    public int getEnemy() {
        return enemy;
    }

    /**
     * Current amount of points that the players have gained
     */
    public int getPoints() {
        return points;
    }

    /**
     * Amount of points needed for the attack event to be successful
     */
    public int getPointsMax() {
        return pointsMax;
    }

    /**
     * Either 'active', 'success' or 'failure depending on if the event is ongoing,
     * successfully ended or ended in a loss
     */
    public String getStatus() {
        return status;
    }

    /**
     * The amount of players that are in a mission in the region where the attack event starts at the time
     * the attack event started
     */
    public int getPlayersAtStart() {
        return playersAtStart;
    }

    /**
     * Unsure what this value is
     */
    public int getMaxEventId() {
        return maxEventId;
    }

    /**
     * Season/war number
     */
    public int getSeason() {
        return season;
    }
}
