package helldivers;

import java.util.Date;
import java.util.Map;
import util.MapUtils;

public class DefendEvent {
    /**
     * Season/war number
     */
    private static int season;

    /**
     * Defence event id, probably equal to the total amount of defense events minus 1
     */
    private static int eventId;

    /**
     * UNIX Timestamp of when the attack event started
     */
    private static long startTime;

    /**
     * UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     */
    private static long endTime;

    /**
     * Id of the region in which the planet that is being attacked lies
     */
    private static int region;

    /**
     * Id of the enemy on the attacked planet
     */
    private static int enemy;

    /**
     * Current amount of points that the players have gained
     */
    private static int points;

    /**
     * Amount of points needed for the attack event to be successful
     */
    private static int pointsMax;

    /**
     * Either 'active', 'success' or 'failure depending on if the event is ongoing,
     * succesfully ended or ended in a loss
     */
    private static String status;

    /**
     * Constructor for DefendEvent that takes a map containing values returned by the helldivers API
     * @param values
     */
    public DefendEvent(Map values){
        this(
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
     * @param season Season/war number
     * @param eventId Attack event id, presumed to be equal to the total amount of attack events minus 1
     * @param startTime UNIX Timestamp of when the attack event started
     * @param endTime UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     * @param region Id of the region in which the planet that is being attacked lies
     * @param enemy Current amount of points that the players have gained
     * @param points Current amount of points that the players have gained
     * @param pointsMax Amount of points needed for the attack event to be successful
     * @param status Either 'active', 'success' or 'failure depending on if the event is ongoing,
     *               succesfully ended or ended in a loss
     */
    public DefendEvent(int season, int eventId, long startTime, long endTime, int region,
                       int enemy, int points, int pointsMax, String status){
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

    /**
     * Season/war number
     */
    public static int getSeason() {
        return season;
    }

    public static void setSeason(int season) {
        DefendEvent.season = season;
    }

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
    public static int getEventId() {
        return eventId;
    }

    public static void setEventId(int eventId) {
        DefendEvent.eventId = eventId;
    }

    /**
     * UNIX Timestamp of when the attack event started
     */
    public static long getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        DefendEvent.startTime = startTime;
    }

    /**
     * UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     */
    public static long getEndTime() {
        return endTime;
    }

    public static void setEndTime(long endTime) {
        DefendEvent.endTime = endTime;
    }

    /**
     * Id of the region in which the planet that is being attacked lies
     */
    public static int getRegion() {
        return region;
    }

    public static void setRegion(int region) {
        DefendEvent.region = region;
    }

    /**
     * Id of the enemy on the attacked planet
     */
    public static int getEnemy() {
        return enemy;
    }

    public static void setEnemy(int enemy) {
        DefendEvent.enemy = enemy;
    }

    /**
     * Current amount of points that the players have gained
     */
    public static int getPoints() {
        return points;
    }

    public static void setPoints(int points) {
        DefendEvent.points = points;
    }

    /**
     * Amount of points needed for the attack event to be successful
     */
    public static int getPointsMax() {
        return pointsMax;
    }

    public static void setPointsMax(int pointsMax) {
        DefendEvent.pointsMax = pointsMax;
    }

    /**
     * Either 'active', 'success' or 'failure depending on if the event is ongoing,
     * succesfully ended or ended in a loss
     */
    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        DefendEvent.status = status;
    }
}
