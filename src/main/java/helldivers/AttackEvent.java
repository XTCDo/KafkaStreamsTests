package helldivers;

import java.util.Map;

public class AttackEvent {
    /**
     * Attack event id, presumed to be equal to the total amount of attack events minus 1
     */
    private int eventId;

    /**
     * UNIX Timestamp of when the attack event started
     */
    private long startTime;

    /**
     * UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     */
    private long endTime;

    /**
     * Id of the region in which the planet that is being attacked lies
     */
    private int region;

    /**
     * Id of the enemy on the attacked planet
     */
    private int enemy;

    /**
     * Current amount of points that the players have gained
     */
    private int points;

    /**
     * Amount of points needed for the attack event to be successful
     */
    private int pointsMax;

    /**
     * Either 'active', 'success' or 'failure depending on if the event is ongoing,
     * succesfully ended or ended in a loss
     */
    private String status;

    /**
     * The amount of players that are in a mission in the region where the attack event starts at the time
     * the attack event started
     */
    private int playersAtStart;

    /**
     * Unsure what this value is
     */
    private int maxEventId;

    /**
     * Constructor for AttackEvent that takes a Map containing values returned by the
     * helldivers API
     * @param values
     */
    public AttackEvent(Map values){
        this(
                (int) Math.round((double) values.get("event_id")),
                (long) Math.round((double) values.get("start_time")),
                (long) Math.round((double) values.get("end_time")),
                (int) Math.round((double) values.get("region")),
                (int) Math.round((double) values.get("enemy")),
                (int) Math.round((double) values.get("points")),
                (int) Math.round((double) values.get("points_max")),
                (String) values.get("status"),
                (int) Math.round((double) values.get("players_at_start")),
                (int) Math.round((double) values.get("max_event_id"))
        );
    }

    /**
     * Regular constructor for AttackEvent
     * @param eventId Attack event id, presumed to be equal to the total amount of attack events minus 1
     * @param startTime UNIX Timestamp of when the attack event started
     * @param endTime UNIX Timestamp of when the attack event will end and be lost if the points requirement is not met
     * @param region Id of the region in which the planet that is being attacked lies
     * @param enemy Current amount of points that the players have gained
     * @param points Current amount of points that the players have gained
     * @param pointsMax Amount of points needed for the attack event to be successful
     * @param status Either 'active', 'success' or 'failure depending on if the event is ongoing,
     *               succesfully ended or ended in a loss
     * @param playersAtStart The amount of players that are in a mission in the region where the attack event starts at
     *                       the time the attack event started
     * @param maxEventId Unsure what this value is
     */
    public AttackEvent(int eventId, long startTime, long endTime, int region, int enemy, int points, int pointsMax,
                       String status, int playersAtStart, int maxEventId){
        this.eventId = eventId;
        this.endTime = endTime;
        this.startTime = startTime;
        this.region = region;
        this.enemy = enemy;
        this.points = points;
        this.pointsMax = pointsMax;
        this.status = status;
        this.playersAtStart = playersAtStart;
        this.maxEventId = maxEventId;
    }

    /**
     * Returns the name of the enemy that is being attacked in this AttackEvent
     * @return the name of the enemy that is being attacked in this AttackEvent
     */
    public String getEnemyName(){
        String[] enemies = new String[] {"Bugs", "Cyborgs", "Illuminate"};
        return enemies[getEnemy()];
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getEnemy() {
        return enemy;
    }

    public void setEnemy(int enemy) {
        this.enemy = enemy;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public int getPlayersAtStart() {
        return playersAtStart;
    }

    public void setPlayersAtStart(int playersAtStart) {
        this.playersAtStart = playersAtStart;
    }

    public int getMaxEventId() {
        return maxEventId;
    }

    public void setMaxEventId(int maxEventId) {
        this.maxEventId = maxEventId;
    }
}
