package ir.ac.kntu.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("A user with this usernamem exists!");
    }
}
