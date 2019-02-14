package helldivers;

import java.util.Map;

public class AttackEvent {
    private int eventId;
    private long startTime;
    private long endTime;
    private int region;
    private int enemy;
    private int points;
    private int pointsMax;
    private String status;
    private int playersAtStart;
    private int maxEventId;

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

    public AttackEvent(int eventId, long startTime, long endTime, int region, int enemy, int points, int pointsMax,
                       String status, int playersAtStart, int maxEventId){
        this.eventId = eventId;
        this.endTime = endTime;
        this.region = region;
        this.enemy = enemy;
        this.points = points;
        this.pointsMax = pointsMax;
        this.status = status;
        this.playersAtStart = playersAtStart;
        this.maxEventId = maxEventId;
    }

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
