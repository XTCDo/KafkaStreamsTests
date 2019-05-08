package planets;

import planets.exceptions.InvalidDistanceToSunException;
import planets.exceptions.InvalidGravityException;
import planets.exceptions.InvalidTemperatureException;
import planets.exceptions.InvalidTemperatureRangeException;

public class PlanetBuilder {
    private String name = "";
    private String capitol = "";
    private String color = "";
    private float distanceToSun = 0.0f;
    private float gravity = 0.0f;
    private float temperature = 0.0f;
    private float minimumTemperature = 0.0f;
    private float maximumTemperature = 0.0f;

    /**
     * Sets the name of the Planet
     * @param name the name of the Planet
     * @return The PlanetBuilder
     */
    public PlanetBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the capitol of the Planet
     * @param capitol the capitol of the Planet
     * @return The PlanetBuilder
     */
    public PlanetBuilder setCapitol(String capitol) {
        this.capitol = capitol;
        return this;
    }

    /**
     * Sets the color of the Planet
     * @param color the color of the Planet
     * @return The PlanetBuilder
     */
    public PlanetBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    /**
     * Sets the distanceToSun of the Planet in AU
     * @param distanceToSun The distance to the sun of the Planet in AU
     * @return The PlanetBuilder
     */
    public PlanetBuilder setDistanceToSun(float distanceToSun) {
        this.distanceToSun = distanceToSun;
        return this;
    }

    /**
     * Sets the gravity of the Planet in m/s^2
     * @param gravity The gravity of the Planet in m/s^2
     * @return The PlanetBuilder
     */
    public PlanetBuilder setGravity(float gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * Sets the temperature of the Planet in K
     * @param temperature The temperature of the Planet in K
     * @return The PlanetBuilder
     */
    public PlanetBuilder setTemperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    /**
     * Sets minimum value for temperature of the PlanetVaryingTemperature
     * @param minimumTemperature The minimum value for temperature of the PlanetVaryingTemperature
     * @return The PlanetBuilder
     */
    public PlanetBuilder setMinimumTemperature(float minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
        return this;
    }

    /**
     * Sets maximum value for temperature of the PlanetVaryingTemperature
     * @param maximumTemperature The maximum value for temperature of the PlanetVaryingTemperature
     * @return The PlanetBuilder
     */
    public PlanetBuilder setMaximumTemperature(float maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
        return this;
    }

    /**
     * Builds a Planet with the provided values
     * @return The Planet
     */
    public Planet build() {
        // Gravity must be larger than 0 m/s^2
        if (gravity < 0.0f) {
            throw new InvalidGravityException();
        }
        // Distance to sun must be larger than 0 AU
        if (distanceToSun < 0.0f) {
            throw new InvalidDistanceToSunException();
        }
        // Temperature must be larger than 0 K
        if (temperature < 0.0f) {
            throw new InvalidTemperatureException();
        }
        return new Planet(name, capitol, color, distanceToSun, gravity, temperature);
    }

    /**
     * Builds a PlanetVaryingTemperature with the provided values
     * @return The PlanetVaryingTemperature
     */
    public PlanetVaryingTemperature buildWithVaryingTemperature() {
        // Minimum temperature can not be larger than maximumtemperature
        // Minimum and maximum temperature must be larger than 0 K
        if (minimumTemperature > maximumTemperature
                || minimumTemperature < 0.0f
                || maximumTemperature < 0.0f) {
            throw new InvalidTemperatureRangeException(minimumTemperature, maximumTemperature);
        }
        // Gravity must be larger than 0 m/s^2
        if (gravity < 0.0f) {
            throw new InvalidGravityException();
        }
        // Distance to sun must be larger than 0 AU
        if (distanceToSun < 0.0f) {
            throw new InvalidDistanceToSunException();
        }
        // Temperature must be larger than 0 K
        if (temperature < 0.0f) {
            throw new InvalidTemperatureException();
        }
        return new PlanetVaryingTemperature(name, capitol, color,
                distanceToSun, gravity, minimumTemperature, maximumTemperature);
    }
}
