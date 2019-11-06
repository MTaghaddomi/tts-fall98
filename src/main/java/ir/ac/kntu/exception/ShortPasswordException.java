package ir.ac.kntu.exception;

public class ShortPasswordException extends WeakPasswordException {
    public ShortPasswordException() {
        super("password is short");
    }
}
