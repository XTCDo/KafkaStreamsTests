package planets.exceptions;

public class InvalidTemperatureException extends RuntimeException {
    @Override
    public String toString() {
        return "temperature cannot be lower than 0.0f";
    }
}
