package ir.ac.kntu.exception;

public class InvalidPassword extends RuntimeException {
    public InvalidPassword(String message) {
        super(message);
    }

    public InvalidPassword() {
        super("password is invalid");
    }
}
