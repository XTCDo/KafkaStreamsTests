package helldivers;

import org.apache.kafka.common.metrics.Stat;

import java.util.Map;

public class Statistics {
    private int season;
    private long seasonDuration;
    private int enemy;
    private int players;
    private int totalUniquePlayers;
    private int missions;
    private int successfulMissions;
    private int totalMissionDifficulty;
    private int completedPlanets;
    private int defendEvents;
    private int successfulDefendEvents;
    private int attackEvents;
    private int successfulAttackEvents;
    private int deaths;
    private int accidentals;
    private long shots;
    private long hits;
    private int kills;

    public Statistics(Map values){
        this(
                (int) Math.round((double) values.get("season")),
                (long) Math.round((double) values.get("season_duration")),
                (int) Math.round((double) values.get("enemy")),
                (int) Math.round((double) values.get("players")),
                (int) Math.round((double) values.get("total_unique_players")),
                (int) Math.round((double) values.get("missions")),
                (int) Math.round((double) values.get("successful_missions")),
                (int) Math.round((double) values.get("total_mission_difficulty")),
                (int) Math.round((double) values.get("completed_planets")),
                (int) Math.round((double) values.get("defend_events")),
                (int) Math.round((double) values.get("successful_defend_events")),
                (int) Math.round((double) values.get("attack_events")),
                (int) Math.round((double) values.get("successful_attack_events")),
                (int) Math.round((double) values.get("deaths")),
                (int) Math.round((double) values.get("accidentals")),
                (int) Math.round((double) values.get("shots")),
                (int) Math.round((double) values.get("hits")),
                (int) Math.round((double) values.get("kills"))
        );
    }

    public Statistics(int season, long seasonDuration, int enemy, int players, int totalUniquePlayers,
                      int missions, int successfulMissions, int totalMissionDifficulty, int completedPlanets, int defendEvents,
                      int successfulDefendEvents, int attackEvents, int successfulAttackEvents,
                      int deaths, int accidentals, long shots, long hits, int kills){
        this.season = season;
        this.seasonDuration = seasonDuration;
        this.enemy = enemy;
        this.players = players;
        this.totalUniquePlayers = totalUniquePlayers;
        this.missions = missions;
        this.successfulMissions = successfulMissions;
        this.totalMissionDifficulty = totalMissionDifficulty;
        this.completedPlanets = completedPlanets;
        this.defendEvents = defendEvents;
        this.successfulDefendEvents = successfulDefendEvents;
        this.attackEvents = attackEvents;
        this.successfulAttackEvents = successfulAttackEvents;
        this.deaths = deaths;
        this.accidentals = accidentals;
        this.shots = shots;
        this.hits = hits;
        this.kills = kills;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public long getSeasonDuration() {
        return seasonDuration;
    }

    public void setSeasonDuration(long seasonDuration) {
        this.seasonDuration = seasonDuration;
    }

    public int getEnemy() {
        return enemy;
    }

    public void setEnemy(int enemy) {
        this.enemy = enemy;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getTotalUniquePlayers() {
        return totalUniquePlayers;
    }

    public void setTotalUniquePlayers(int totalUniquePlayers) {
        this.totalUniquePlayers = totalUniquePlayers;
    }

    public int getMissions() {
        return missions;
    }

    public void setMissions(int missions) {
        this.missions = missions;
    }

    public int getSuccessfulMissions() {
        return successfulMissions;
    }

    public void setSuccessfulMissions(int successfulMissions) {
        this.successfulMissions = successfulMissions;
    }

    public int getTotalMissionDifficulty() {
        return totalMissionDifficulty;
    }

    public void setTotalMissionDifficulty(int totalMissionDifficulty) {
        this.totalMissionDifficulty = totalMissionDifficulty;
    }

    public int getCompletedPlanets() {
        return completedPlanets;
    }

    public void setCompletedPlanets(int completedPlanets) {
        this.completedPlanets = completedPlanets;
    }

    public int getDefendEvents() {
        return defendEvents;
    }

    public void setDefendEvents(int defendEvents) {
        this.defendEvents = defendEvents;
    }

    public int getSuccessfulDefendEvents() {
        return successfulDefendEvents;
    }

    public void setSuccessfulDefendEvents(int successfulDefendEvents) {
        this.successfulDefendEvents = successfulDefendEvents;
    }

    public int getAttackEvents() {
        return attackEvents;
    }

    public void setAttackEvents(int attackEvents) {
        this.attackEvents = attackEvents;
    }

    public int getSuccessfulAttackEvents() {
        return successfulAttackEvents;
    }

    public void setSuccessfulAttackEvents(int successfulAttackEvents) {
        this.successfulAttackEvents = successfulAttackEvents;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAccidentals() {
        return accidentals;
    }

    public void setAccidentals(int accidentals) {
        this.accidentals = accidentals;
    }

    public long getShots() {
        return shots;
    }

    public void setShots(long shots) {
        this.shots = shots;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}
