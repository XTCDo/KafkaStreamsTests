package kafka;

public class Planet {
    private String name;
    private String capitol;
    private String color;
    private float distanceToSun;
    private float gravity;
    private float temperature;

    public Planet(String name, String capitol, String color, float distanceToSun, float gravity, float temperature) {
        this.setName(name);
        this.setCapitol(capitol);
        this.setColor(color);
        this.setDistanceToSun(distanceToSun);
        this.setGravity(gravity);
        this.setTemperature(temperature);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(":")
                .append(capitol).append(":")
                .append(color).append(":")
                .append(distanceToSun).append(":")
                .append(gravity).append(":")
                .append(temperature);
        return new String(builder);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitol() {
        return capitol;
    }

    public void setCapitol(String capitol) {
        this.capitol = capitol;
    }

    public float getDistanceToSun() {
        return distanceToSun;
    }

    public void setDistanceToSun(float distanceToSun) {
        this.distanceToSun = distanceToSun;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
