package planets.exceptions;

public class InvalidDistanceToSunException extends RuntimeException {
    @Override
    public String toString() {
        return "gravity cannot be lower than 0.0f";
    }
}
