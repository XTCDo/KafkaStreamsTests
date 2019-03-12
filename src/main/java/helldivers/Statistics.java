package helldivers;

import com.google.gson.Gson;

import java.util.Map;

public class Statistics {
    /**
     * Season/war number
     */
    private int season;

    /**
     * Amount of seconds that the current war has been going on
     */
    private long seasonDuration;

    /**
     * The id of the enemy these statistics belong to
     */
    private int enemy;

    /**
     * Amount of players currently online (in this region?)
     */
    private int players;

    /**
     * Amount of unique players that have fought this season/war
     */
    private int totalUniquePlayers;

    /**
     * Amount of missions played
     */
    private int missions;

    /**
     * Amount of missions played that were successful
     */
    private int successfulMissions;

    /**
     * Sum of the mission difficulties of all successful missions
     */
    private int totalMissionDifficulty;

    /**
     * Amount of planets where all missions were finished
     */
    private int completedPlanets;

    /**
     * Amount of defend events
     */
    private int defendEvents;

    /**
     * Amount of successful defend events
     */
    private int successfulDefendEvents;

    /**
     * Amount of attack events
     */
    private int attackEvents;

    /**
     * Amount of successful attack events
     */
    private int successfulAttackEvents;

    /**
     * Amount of player deaths
     */
    private int deaths;

    /**
     * Amount of player-caused deaths
     */
    private int accidentals;

    /**
     * Amount of shots fired by players
     */
    private long shots;

    /**
     * Amount of shots fired by players that hit
     */
    private long hits;

    /**
     * Amount of enemies killed
     */
    private int kills;

    /**
     * Constructor for Statistics that takes a Map containing values returned by the helldivers API
     * @param values a map containing the statistics
     */
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

    /**
     * Regular constructor fo Statistics
     * @param season Season/war number
     * @param seasonDuration Amount of seconds that the current war has been going on
     * @param enemy The id of the enemy these statistics belong to
     * @param players Amount of players currently online (in this region?)
     * @param totalUniquePlayers Amount of unique players that have fought this season/war
     * @param missions Amount of missions played
     * @param successfulMissions Amount of missions played that were successful
     * @param totalMissionDifficulty Sum of the mission difficulties of all successful missions
     * @param completedPlanets Amount of planets where all missions were finished
     * @param defendEvents Amount of defend events
     * @param successfulDefendEvents Amount of successful defend events
     * @param attackEvents Amount of attack events
     * @param successfulAttackEvents Amount of successful attack events
     * @param deaths Amount of player deaths
     * @param accidentals Amount of player-caused deaths
     * @param shots Amount of shots fired by players
     * @param hits Amount of shots fired by players that hit
     * @param kills Amount of enemies killed
     */
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

    /**
     * Returns the name of the enemy that is being attacked in this AttackEvent
     * @return the name of the enemy that is being attacked in this AttackEvent
     */
    public String getEnemyName(){
        String[] enemies = new String[] {"Bugs", "Cyborgs", "Illuminate"};
        return enemies[getEnemy()];
    }


    /**
     * Returns a String describing the Statistics
     * @return a String describing the Statistics
     */
    public String getDescription(){
        StringBuilder description = new StringBuilder();
        description.append(this.toString()).append("\n")
                .append("Enemy:                    ").append(getEnemyName()).append("\n")
                .append("War:                      ").append(getSeason()).append("\n")
                .append("Season duration:          ").append(getSeasonDuration()).append("s\n")
                .append("Players:                  ").append(getPlayers()).append("\n")
                .append("Total unique players:     ").append(getTotalUniquePlayers()).append("\n")
                .append("Missions:                 ").append(getMissions()).append("\n")
                .append("Successful missions:      ").append(getSuccessfulMissions()).append("\n")
                .append("Total mission difficulty: ").append(getTotalMissionDifficulty()).append("\n")
                .append("Completed planets:        ").append(getCompletedPlanets()).append("\n")
                .append("Defend events:            ").append(getDefendEvents()).append("\n")
                .append("Successful defend events: ").append(getSuccessfulDefendEvents()).append("\n")
                .append("Attack events:            ").append(getAttackEvents()).append("\n")
                .append("Successful attack events: ").append(getSuccessfulAttackEvents()).append("\n")
                .append("Death:                    ").append(getDeaths()).append("\n")
                .append("Accidentals:              ").append(getAccidentals()).append("\n")
                .append("Accidentals %:            ").append(100*getAccidentals()/getDeaths()).append("%\n")
                .append("Shots fired:              ").append(getShots()).append("\n")
                .append("Shots hit:                ").append(getHits()).append("\n")
                .append("Accuracy:                 ").append((100*getHits()/getShots())).append("%\n")
                .append("Enemies killed:           ").append(getKills()).append("\n");
        return description.toString();
    }

    /**
     * Season/war number
     */
    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    /**
     * Amount of seconds that the current war has been going on
     */
    public long getSeasonDuration() {
        return seasonDuration;
    }

    public void setSeasonDuration(long seasonDuration) {
        this.seasonDuration = seasonDuration;
    }

    /**
     * The id of the enemy these statistics belong to
     */
    public int getEnemy() {
        return enemy;
    }

    public void setEnemy(int enemy) {
        this.enemy = enemy;
    }

    /**
     * Amount of players currently online (in this region?)
     */
    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    /**
     * Amount of unique players that have fought this season/war
     */
    public int getTotalUniquePlayers() {
        return totalUniquePlayers;
    }

    public void setTotalUniquePlayers(int totalUniquePlayers) {
        this.totalUniquePlayers = totalUniquePlayers;
    }

    /**
     * Amount of missions played
     */
    public int getMissions() {
        return missions;
    }

    public void setMissions(int missions) {
        this.missions = missions;
    }

    /**
     * Amount of missions played that were successful
     */
    public int getSuccessfulMissions() {
        return successfulMissions;
    }

    public void setSuccessfulMissions(int successfulMissions) {
        this.successfulMissions = successfulMissions;
    }

    /**
     * Sum of the mission difficulties of all successful missions
     */
    public int getTotalMissionDifficulty() {
        return totalMissionDifficulty;
    }

    public void setTotalMissionDifficulty(int totalMissionDifficulty) {
        this.totalMissionDifficulty = totalMissionDifficulty;
    }

    /**
     * Amount of planets where all missions were finished
     */
    public int getCompletedPlanets() {
        return completedPlanets;
    }

    public void setCompletedPlanets(int completedPlanets) {
        this.completedPlanets = completedPlanets;
    }

    /**
     * Amount of defend events
     */
    public int getDefendEvents() {
        return defendEvents;
    }

    public void setDefendEvents(int defendEvents) {
        this.defendEvents = defendEvents;
    }

    /**
     * Amount of successful defend events
     */
    public int getSuccessfulDefendEvents() {
        return successfulDefendEvents;
    }

    public void setSuccessfulDefendEvents(int successfulDefendEvents) {
        this.successfulDefendEvents = successfulDefendEvents;
    }

    /**
     * Amount of attack events
     */
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
