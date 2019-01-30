package planets.test;

import planets.Planet;
import planets.PlanetBuilder;

public class TestPlanetBuilder {
    public static void main(String[] args){
        PlanetBuilder pb = new PlanetBuilder();
        Planet djop = pb.setName("Diepenbeek")
                .setCapitol("Toekomststraat")
                .setColor("Grey")
                .setDistanceToSun(1.0f)
                .setGravity(9.81f)
                .setTemperature(290.0f)
                .build();

        djop.describe();
    }
}
