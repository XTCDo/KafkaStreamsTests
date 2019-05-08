package util.exceptions;

public class InvalidInfluxMapException extends RuntimeException {
    public InvalidInfluxMapException(){ }

    @Override
    public String toString() {
        return "InvalidInfluxMapException";
    }
}
