package planets.exceptions;

public class InvalidGravityException extends RuntimeException {
    @Override
    public String toString() {
        return "InvalidGravityException: gravity cannot be lower than 0.0f";
    }
}
