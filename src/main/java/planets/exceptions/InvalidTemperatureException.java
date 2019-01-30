package planets.exceptions;

public class InvalidTemperatureException extends RuntimeException {
    @Override
    public String toString() {
        return "InvalidTemperatureException: temperature cannot be lower than 0.0f";
    }
}
