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
        PlanetBuilder pb = new PlanetBuilder();
        Planet djop = pb.setName("Diepenbeek")
                .setCapitol("Toekomststraat")
                .setColor("Grey")
                .setDistanceToSun(1.0f)
                .setGravity(9.81f)
                .setTemperature(290.0f)
                .build();

        djop.describe();

        pb = new PlanetBuilder();
        Planet randomDjop = pb.setName("Diepenbeek")
                .setCapitol("Toekomststraat")
                .setColor("grey")
                .setDistanceToSun(1.0f)
                .setGravity(9.81f)
                .setMaximumTemperature(270.0f + 40.0f)
                .setMinimumTemperature(270.f + 40.0f)
                .buildWithVaryingTemperature();

        for(int i = 0; i < 5; i++){
            randomDjop.describe();
        }

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

        try {
            pb = new PlanetBuilder();
            PlanetVaryingTemperature largerMinimumTemperaturePlanet =
                    pb.setMinimumTemperature(100.0f)
                        .setMaximumTemperature(0.0f)
                        .buildWithVaryingTemperature();
        } catch (InvalidTemperatureRangeException itre){
            itre.printStackTrace();
        }

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
