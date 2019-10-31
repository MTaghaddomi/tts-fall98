package ir.ac.kntu.SAD_fall98.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("User exist!");
    }
}
