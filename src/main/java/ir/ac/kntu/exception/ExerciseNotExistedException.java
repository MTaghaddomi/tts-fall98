package ir.ac.kntu.exception;

public class ExerciseNotExistedException extends RuntimeException {
    public ExerciseNotExistedException() {
        super("Exercise Is Not Existed!");
    }
}
