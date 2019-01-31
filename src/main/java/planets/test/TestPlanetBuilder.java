package planets.test;

import planets.Planet;
import planets.PlanetBuilder;
import planets.PlanetVaryingTemperature;
import planets.exceptions.InvalidDistanceToSunException;
import planets.exceptions.InvalidGravityException;
import planets.exceptions.InvalidTemperatureException;
import planets.exceptions.InvalidTemperatureRangeException;

public class TestPlanetBuilder {
    public static void main(String[] args){

        // Create a regular planet and describe it
        PlanetBuilder pb = new PlanetBuilder();
        Planet djop = pb.setName("Diepenbeek")
                .setCapitol("Toekomststraat")
                .setColor("Grey")
                .setDistanceToSun(1.0f)
                .setGravity(9.81f)
                .setTemperature(290.0f)
                .build();
        djop.describe();

        // Create a PlanetVaryingTemperature and describe it
        // 5 times to see that the temperature actually varies
        pb = new PlanetBuilder();
        Planet randomDjop = pb.setName("Diepenbeek")
                .setCapitol("Toekomststraat")
                .setColor("grey")
                .setDistanceToSun(1.0f)
                .setGravity(9.81f)
                .setMaximumTemperature(270.0f + 40.0f)
                .setMinimumTemperature(270.f - 40.0f)
                .buildWithVaryingTemperature();

        for(int i = 0; i < 5; i++){
            randomDjop.describe();
        }

        // Create a planet with an invalid temperature to
        // test if an InvalidTemperatureException is thrown
        try {
            pb = new PlanetBuilder();
            Planet invalidTemperaturePlanet = pb.setTemperature(-1.0f).build();
        } catch(InvalidTemperatureException ite){
            ite.printStackTrace();
        }

        // Create a planet with an invalid gravity to
        // test if an InvalidGravityException is thrown
        try {
            pb = new PlanetBuilder();
            Planet invalidGravityPlanet = pb.setGravity(-1.0f).build();
        } catch (InvalidGravityException ige){
            ige.printStackTrace();
        }

        // Create a planet with an invalid distanceToSun to
        // test if an InvalidDistanceToSunException is thrown
        try {
            pb = new PlanetBuilder();
            Planet invalidDistanceToSunPlanet = pb.setDistanceToSun(-1.0f).build();
        } catch (InvalidDistanceToSunException idtse){
            idtse.printStackTrace();
        }

        // Create a planet with a minimum temperature larger than the maximum
        // to test if an InvalidTemperatureRangeException is thrown
        try {
            pb = new PlanetBuilder();
            PlanetVaryingTemperature largerMinimumTemperaturePlanet =
                    pb.setMinimumTemperature(100.0f)
                        .setMaximumTemperature(0.0f)
                        .buildWithVaryingTemperature();
        } catch (InvalidTemperatureRangeException itre){
            itre.printStackTrace();
        }

        // Create a planet with an invalid minimum temperature to
        // test if an InvalidTemperatureRangeException is thrown
        try {
            pb = new PlanetBuilder();
            PlanetVaryingTemperature invalidMinimumTemperaturePlanet =
                    pb.setMinimumTemperature(-1.0f)
                    .buildWithVaryingTemperature();
        } catch (InvalidTemperatureRangeException itre){
            itre.printStackTrace();
        }

    }
}
