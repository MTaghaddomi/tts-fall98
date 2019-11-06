package ir.ac.kntu.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("User exist!");
    }
}
