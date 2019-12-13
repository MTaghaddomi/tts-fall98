package ir.ac.kntu.exception;

public class AnswerNotExistedException extends RuntimeException {
    public AnswerNotExistedException() {
        super("Answer Is Not Existed!");
    }
}
