package helldivers;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.influxdb.dto.Point;
import util.MapUtils;

public class Statistics {

    /**
     * The time at which this measurement was made
     */
    private final long timeStamp;

    /**
     * Season/war number
     */
    private final int season;

    /**
     * Amount of seconds that the current war has been going on
     */
    private final long seasonDuration;

    /**
     * The id of the enemy these statistics belong to
     */
    private final int enemy;

    /**
     * Amount of players currently online (in this region?)
     */
    private final int players;

    /**
     * Amount of unique players that have fought this season/war
     */
    private final int totalUniquePlayers;

    /**
     * Amount of missions played
     */
    private final int missions;

    /**
     * Amount of missions played that were successful
     */
    private final int successfulMissions;

    /**
     * Sum of the mission difficulties of all successful missions
     */
    private final int totalMissionDifficulty;

    /**
     * Amount of planets where all missions were finished
     */
    private final int completedPlanets;

    /**
     * Amount of defend events
     */
    private final int defendEvents;

    /**
     * Amount of successful defend events
     */
    private final int successfulDefendEvents;

    /**
     * Amount of attack events
     */
    private final int attackEvents;

    /**
     * Amount of successful attack events
     */
    private final int successfulAttackEvents;

    /**
     * Amount of player deaths
     */
    private final int deaths;

    /**
     * Amount of player-caused deaths
     */
    private final int accidentals;

    /**
     * Amount of shots fired by players
     */
    private final long shots;

    /**
     * Amount of shots fired by players that hit
     */
    private final long hits;

    /**
     * Amount of enemies killed
     */
    private final int kills;

    /**
     * Constructor for Statistics that takes a Map containing values returned by the helldivers API
     * @param values a map containing the statistics
     */
    public Statistics(Map values) {
        this(
            values.get("timeStamp"),
            (int) Math.round((double) values.get("season")),
            (int) Math.round((double) MapUtils.safeGet(values,"season_duration")),
            (int) Math.round((double) values.get("enemy")),
            (int) Math.round((double) values.get("players")),
            (int) Math.round((double) MapUtils.safeGet(values,"total_unique_players")),
            (int) Math.round((double) values.get("missions")),
            (int) Math.round((double) MapUtils.safeGet(values,"successful_missions")),
            (int) Math.round((double) MapUtils.safeGet(values,"total_mission_difficulty")),
            (int) Math.round((double) MapUtils.safeGet(values,"completed_planets")),
            (int) Math.round((double) MapUtils.safeGet(values,"defend_events")),
            (int) Math.round((double) MapUtils.safeGet(values,"successful_defend_events")),
            (int) Math.round((double) MapUtils.safeGet(values,"attack_events")),
            (int) Math.round((double) MapUtils.safeGet(values,"successful_attack_events")),
            (int) Math.round((double) values.get("deaths")),
            (int) Math.round((double) values.get("accidentals")),
            (int) Math.round((double) values.get("shots")),
            (int) Math.round((double) values.get("hits")),
            (int) Math.round((double) values.get("kills"))
        );
    }

    /**
     * cast this object to an influx point
     * @param table name of the measurement
     * @return influx Point representing this object
     */
    public Point toPoint(String table) {
        return Point.measurement(table)
            .time(getTimeStamp(), TimeUnit.MILLISECONDS)
            // tags
            .tag("season", String.valueOf(getSeason()))
            .tag("enemy", getEnemyName())
            // general season info
            .addField("season_duration", getSeasonDuration())
            .addField("deaths", getDeaths())
            .addField("accidentals", getAccidentals())
            .addField("shots", getShots())
            .addField("hits", getHits())
            .addField("kills", getKills())
            // players info
            .addField("players", getPlayers())
            .addField("total_unique_players", getTotalUniquePlayers())
            // missions
            .addField("missions", getMissions())
            .addField("successful_missions", getSuccessfulMissions())
            .addField("total_mission_difficulty", getTotalMissionDifficulty())
            .addField("completed_planets", getCompletedPlanets())
            // events
            .addField("defend_events", getDefendEvents())
            .addField("successful_defend_events", getSuccessfulDefendEvents())
            .addField("attack_events", getAttackEvents())
            .addField("successful_attack_events", getSuccessfulAttackEvents())
            .build();
    }

    /**
     * Regular constructor fo Statistics
     *
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
    public Statistics(Object timeStamp, int season, long seasonDuration, int enemy,
                      int players, int totalUniquePlayers,
                      int missions, int successfulMissions, int totalMissionDifficulty,
                      int completedPlanets,
                      int defendEvents, int successfulDefendEvents,
                      int attackEvents, int successfulAttackEvents,
                      int deaths, int accidentals,
                      long shots, long hits, int kills) {
        this.timeStamp = (timeStamp instanceof Long) ? (long) timeStamp : Math.round( (double) timeStamp);
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
     *
     * @return the name of the enemy that is being attacked in this AttackEvent
     */
    public String getEnemyName() {
        String[] enemies = new String[]{"Bugs", "Cyborgs", "Illuminate"};
        return enemies[getEnemy()];
    }


    /**
     * Returns a String describing the Statistics
     *
     * @return a String describing the Statistics
     */
    public String getDescription() {
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
            .append("Accidentals %:            ").append(getAccidentalsPercentage())
            .append("%\n")
            .append("Shots fired:              ").append(getShots()).append("\n")
            .append("Shots hit:                ").append(getHits()).append("\n")
            .append("Accuracy:                 ").append(getAccuracy())
            .append("%\n")
            .append("Enemies killed:           ").append(getKills()).append("\n");
        return description.toString();
    }

    /**
     * Get accuracy / percentage of shots hit Always use this method to get the accuracy, don't
     * calculate it yourself because sometimes the result of getShots() is 0 when the faction to
     * which the statistics are related to isn't active yet, resulting in an error by trying to
     * divide by zero
     *
     * @return Accuracy / percentage of shots hit
     */
    public double getAccuracy() {
        if (getShots() == 0) {
            return 0;
        } else {
            return 100 * getHits() / getShots();
        }
    }

    /**
     * Gets amount of deaths attributed to accidentals Always use this method to get the percentage
     * of accidentals, don't calculate it yourself because sometimes the result of getDeaths() is 0
     * when the factio nto which the statistics are related to isn't active yet, resulting in an
     * error by trying to divide by zero
     *
     * @return Amount of deaths attributed to accidentals
     */
    public double getAccidentalsPercentage() {
        if (getDeaths() == 0) {
            return 0;
        } else {
            return 100 * getAccidentals() / getDeaths();
        }
    }

    /**
     * Season/war number
     */
    public int getSeason() {
        return season;
    }

    /**
     * Amount of seconds that the current war has been going on
     */
    public long getSeasonDuration() {
        return seasonDuration;
    }


    /**
     * The id of the enemy these statistics belong to
     */
    public int getEnemy() {
        return enemy;
    }


    /**
     * Amount of players currently online (in this region?)
     */
    public int getPlayers() {
        return players;
    }


    /**
     * Amount of unique players that have fought this season/war
     */
    public int getTotalUniquePlayers() {
        return totalUniquePlayers;
    }


    /**
     * Amount of missions played
     */
    public int getMissions() {
        return missions;
    }


    /**
     * Amount of missions played that were successful
     */
    public int getSuccessfulMissions() {
        return successfulMissions;
    }


    /**
     * Sum of the mission difficulties of all successful missions
     */
    public int getTotalMissionDifficulty() {
        return totalMissionDifficulty;
    }


    /**
     * Amount of planets where all missions were finished
     */
    public int getCompletedPlanets() {
        return completedPlanets;
    }


    /**
     * Amount of defend events
     */
    public int getDefendEvents() {
        return defendEvents;
    }


    /**
     * Amount of successful defend events
     */
    public int getSuccessfulDefendEvents() {
        return successfulDefendEvents;
    }


    /**
     * Amount of attack events
     */
    public int getAttackEvents() {
        return attackEvents;
    }


    /**
     * @return the number of successful attack events
     */
    public int getSuccessfulAttackEvents() {
        return successfulAttackEvents;
    }


    /**
     * Amount of player deaths
     */
    public int getDeaths() {
        return deaths;
    }


    /**
     * Amount of player-caused deaths
     */
    public int getAccidentals() {
        return accidentals;
    }


    /**
     * Amount of shots fired by players
     */
    public long getShots() {
        return shots;
    }

    /**
     * Amount of shots fired by players that hit
     */
    public long getHits() {
        return hits;
    }


    /**
     * Amount of enemies killed
     */
    public int getKills() {
        return kills;
    }


    /**
     * The time at which this measurement was made
     */
    public long getTimeStamp() {
        return timeStamp;
    }
}
