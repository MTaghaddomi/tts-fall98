package ir.ac.kntu.exception;

public class UserNotExistedException extends RuntimeException {
    public UserNotExistedException() {
        super("User Is Not Existed!");
    }
}
