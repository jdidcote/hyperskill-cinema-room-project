package cinema.exceptions;

public class InvalidReturnTokenError extends Exception {
    public InvalidReturnTokenError(String errorMessage) {
        super(errorMessage);
    }
}