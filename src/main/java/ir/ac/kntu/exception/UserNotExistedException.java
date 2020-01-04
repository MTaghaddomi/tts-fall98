package ir.ac.kntu.exception;

public class UserNotExistedException extends RuntimeException {
    public UserNotExistedException() {
        super("کاربری با این یوزرنیم وجود ندارد.");
    }
}
