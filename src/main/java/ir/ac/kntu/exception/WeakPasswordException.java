package ir.ac.kntu.exception;

public class WeakPasswordException extends InvalidPassword {
    public WeakPasswordException() {
        super("password is weak");
    }

    public WeakPasswordException(String message) {
        super(message);
    }
}
