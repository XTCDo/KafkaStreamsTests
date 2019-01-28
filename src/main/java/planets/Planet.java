package planets;

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

    /**
     * Creates a new Planet out of a structured String
     * name:capitol:color:distanceToSun:gravity:temperature
     * @param planetAsString Structured String that describes the Planet
     */
    public Planet(String planetAsString){
        String[] data = planetAsString.split(":");
        name = data[0];
        capitol = data[1];
        color = data[2];
        distanceToSun = parseFloatWithDefault(data[3], -1.0f);
        gravity = parseFloatWithDefault(data[4], -1.0f);
        temperature = parseFloatWithDefault(data[5], -1.0f);
    }

    /**
     * Turns the planets into a structured String
     * @return The structured String
     */
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

    /**
     * Prints out a description of the Planet
     */
    public void describe(){
        System.out.printf("Planet: %s\n", name);
        System.out.printf("\tCapitol:\t\t%s\n", capitol);
        System.out.printf("\tColor:\t\t\t%s\n", color);
        System.out.printf("\tDistance to Sun:\t%s AU\n", distanceToSun);
        System.out.printf("\tGravity:\t\t%s m/s^2\n", gravity);
        System.out.printf("\tTemperature:\t\t%s K\n", temperature);
    }

    private float parseFloatWithDefault(String value, float defaultValue){
        float valueAsFloat = defaultValue;
        try{
            valueAsFloat = Float.parseFloat(value);
        } catch (NumberFormatException nfe){
            nfe.printStackTrace();
        } finally {
            return valueAsFloat;
        }

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
