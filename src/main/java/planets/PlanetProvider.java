package planets;

import java.util.ArrayList;
import java.util.List;

public class PlanetProvider {
    public static List<Planet> getPlanets(){
        List<Planet> planets = new ArrayList<>();
        planets.add(new Planet(
                "Mercury",
                "Sydney",
                "brown",
                0.39f,
                3.7f,
                452
        ));
        planets.add(new Planet(
                "Venus",
                "",
                "light_blue",
                0.723f,
                8.87f,
                726
        ));
        planets.add(new Planet(
                "Terra",
                "Diepenbeek",
                "blue",
                1f,
                9.81f,
                310
        ));
        planets.add(new Planet(
                "Mars",
                "Valis Marineris",
                "red/brown",
                1.524f,
                3.711f,
                150
        ));
        planets.add(new Planet(
                "Jupiter",
                "",
                "brown",
                5.203f,
                24.79f,
                120
        ));
        planets.add(new Planet(
                "Saturn",
                "",
                "light_brown",
                9.539f,
                10.44f,
                88
        ));
        planets.add(new Planet(
                "Uranus",
                "",
                "white_blue",
                19.18f,
                8.87f,
                59
        ));
        planets.add(new Planet(
                "Neptune",
                "",
                "dark_blue",
                30.06f,
                11.15f,
                48
        ));
        planets.add(new Planet(
                "Pluto",
                "",
                "grey/brown",
                39.53f,
                0.62f,
                37
        ));
        return planets;
    }
}
