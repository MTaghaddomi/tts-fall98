package ir.ac.kntu.exception;

public class NotEnoughAccessLevelException extends RuntimeException {
    public NotEnoughAccessLevelException() {
        super("Not Permitted Because of Low Access Level");
    }
}
