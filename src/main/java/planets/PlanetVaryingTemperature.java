package planets;

import util.RandomUtils;

import java.util.Random;

public class PlanetVaryingTemperature extends Planet {
    private float minimumTemperature;
    private float maximumTemperature;
    public PlanetVaryingTemperature(String name, String capitol,
                                    String color, float distanceToSun,
                                    float gravity, float minimumTemperature,
                                    float maximumTemperature) {
        super(name, capitol, color, distanceToSun, gravity,
                RandomUtils.randomFloat(minimumTemperature, maximumTemperature));
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
    }

    @Override
    public float getTemperature() {
        return RandomUtils.randomFloat(minimumTemperature, maximumTemperature);
    }
}
