package helldivers;

import java.util.Date;
import java.util.Map;

public class DefendEvent {
    private static int eventId;
    private static long startTime;
    private static long endTime;
    private static int region;
    private static int enemy;
    private static int points;
    private static int pointsMax;
    private static String status;

    public DefendEvent(Map values){
        this(
            (int) Math.round((double) values.get("event_id")),
            (long) Math.round((double) values.get("start_time")),
            (long) Math.round((double) values.get("end_time")),
            (int) Math.round((double) values.get("region")),
            (int) Math.round((double) values.get("enemy")),
            (int) Math.round((double) values.get("points")),
            (int) Math.round((double) values.get("points_max")),
            (String) values.get("status")
        );
    }

    public DefendEvent(int eventId, long startTime, long endTime, int region,
                       int enemy, int points, int pointsMax, String status){
        this.eventId = eventId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.region = region;
        this.enemy = enemy;
        this.points = points;
        this.pointsMax = pointsMax;
        this.status = status;
    }

    public String getDescription(){
        StringBuilder description = new StringBuilder();
        description.append("Enemy:      ").append(getEnemyName()).append("\n")
                .append("Status:     ").append(getStatus()).append("\n")
                .append("Start time: ").append(new Date(getStartTime())).append("\n")
                .append("End time:   ").append(new Date(getEndTime())).append("\n")
                .append("Region      ").append(getRegion()).append("\n")
                .append("Points:     ").append(getPoints()).append("\n")
                .append("Points Max: ").append(getPointsMax()).append("\n");
        return description.toString();
    }

    public String getEnemyName(){
        String[] enemies = new String[] {"Bugs", "Cyborgs", "Illuminate"};
        return enemies[getEnemy()];
    }

    public static int getEventId() {
        return eventId;
    }

    public static void setEventId(int eventId) {
        DefendEvent.eventId = eventId;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        DefendEvent.startTime = startTime;
    }

    public static long getEndTime() {
        return endTime;
    }

    public static void setEndTime(long endTime) {
        DefendEvent.endTime = endTime;
    }

    public static int getRegion() {
        return region;
    }

    public static void setRegion(int region) {
        DefendEvent.region = region;
    }

    public static int getEnemy() {
        return enemy;
    }

    public static void setEnemy(int enemy) {
        DefendEvent.enemy = enemy;
    }

    public static int getPoints() {
        return points;
    }

    public static void setPoints(int points) {
        DefendEvent.points = points;
    }

    public static int getPointsMax() {
        return pointsMax;
    }

    public static void setPointsMax(int pointsMax) {
        DefendEvent.pointsMax = pointsMax;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        DefendEvent.status = status;
    }
}
