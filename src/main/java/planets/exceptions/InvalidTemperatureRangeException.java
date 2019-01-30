package planets.exceptions;

public class InvalidTemperatureRangeException extends RuntimeException {
    private float minimumTemperature;
    private float maximumTemperature;

    public InvalidTemperatureRangeException(float minimumTemperature, float maximumTemperature){
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
    }

    public String toString(){
        if(minimumTemperature > maximumTemperature){
            return "minimumTemperature cannot be larger than maximumTemperature";
        } else if (minimumTemperature < 0.0f){
            return "minimumTemperature cannot be lower than 0.0f";
        } else {
            return "maximumTemperature cannot be lower than 0.0f";
        }
    }
}
