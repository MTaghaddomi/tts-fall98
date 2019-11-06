package ir.ac.kntu.SAD_fall98.exception;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException() {
        super("classroom not found! ");
    }

}
