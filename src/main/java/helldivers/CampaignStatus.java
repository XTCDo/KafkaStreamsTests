package helldivers;

public class CampaignStatus {
    private int season;
    private int points;
    private int pointsTaken;
    private int pointsMax;
    private String status;
    private int introductionOrder;

    public CampaignStatus(int season, int points, int pointsTaken, int pointsMax, String status, int introductionOrder){
        this.season = season;
        this.points = points;
        this.pointsTaken = pointsTaken;
        this.pointsMax = pointsMax;
        this.status = status;
        this.introductionOrder = introductionOrder;
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
}
