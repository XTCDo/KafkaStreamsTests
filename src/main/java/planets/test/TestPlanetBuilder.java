package planets.test;

import planets.Planet;
import planets.PlanetBuilder;
import planets.exceptions.InvalidDistanceToSunException;
import planets.exceptions.InvalidGravityException;
import planets.exceptions.InvalidTemperatureException;

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

        try {
            pb = new PlanetBuilder();
            Planet invalidTemperaturePlanet = pb.setTemperature(-1.0f).build();
        } catch(InvalidTemperatureException ite){
            ite.printStackTrace();
        }

        try {
            pb = new PlanetBuilder();
            Planet invalidGravityPlanet = pb.setGravity(-1.0f).build();
        } catch (InvalidGravityException ige){
            ige.printStackTrace();
        }

        try {
            pb = new PlanetBuilder();
            Planet invalidDistanceToSunPlanet = pb.setDistanceToSun(-1.0f).build();
        } catch (InvalidDistanceToSunException idtse){
            idtse.printStackTrace();
        }
    }
}
