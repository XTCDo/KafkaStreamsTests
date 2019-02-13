package helldivers;

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
