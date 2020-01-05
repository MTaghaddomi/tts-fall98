package ir.ac.kntu.exception;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException() {
        super("Classroom not found!");
    }
}
