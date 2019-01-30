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

    public PlanetBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PlanetBuilder setCapitol(String capitol){
        this.capitol = capitol;
        return this;
    }

    public PlanetBuilder setColor(String color){
        this.color = color;
        return this;
    }

    public PlanetBuilder setDistanceToSun(float distanceToSun){
        this.distanceToSun = distanceToSun;
        return this;
    }

    public PlanetBuilder setGravity(float gravity){
        this.gravity = gravity;
        return this;
    }

    public PlanetBuilder setTemperature(float temperature){
        this.temperature = temperature;
        return this;
    }

    public PlanetBuilder setMinimumTemperature(float minimumTemperature){
        this.minimumTemperature = minimumTemperature;
        return this;
    }

    public PlanetBuilder setMaximumTemperature(float maximumTemperature){
        this.maximumTemperature = maximumTemperature;
        return this;
    }

    public Planet build(){
        if (gravity < 0.0f){
            throw new InvalidGravityException();
        }
        if (distanceToSun < 0.0f){
            throw new InvalidDistanceToSunException();
        }
        if (temperature < 0.0f) {
            throw new InvalidTemperatureException();
        }
        return new Planet(name, capitol, color, distanceToSun, gravity, temperature);
    }

    public PlanetVaryingTemperature buildWithVaryingTemperature(){
        if (minimumTemperature > maximumTemperature
                || minimumTemperature < 0.0f
                || maximumTemperature < 0.0f) {
            throw new InvalidTemperatureRangeException(minimumTemperature, maximumTemperature);
        }
        if (gravity < 0.0f){
            throw new InvalidGravityException();
        }
        if (distanceToSun < 0.0f){
            throw new InvalidDistanceToSunException();
        }
        if (temperature < 0.0f) {
            throw new InvalidTemperatureException();
        }
        return new PlanetVaryingTemperature(name, capitol, color,
                distanceToSun, gravity, minimumTemperature, maximumTemperature);
    }
}
