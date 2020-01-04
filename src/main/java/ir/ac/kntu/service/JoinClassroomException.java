package ir.ac.kntu.service;

/**
 * @author Morteza Taghaddomi
 */
public class JoinClassroomException extends RuntimeException {
    public JoinClassroomException() {
        super("Teacher can't join his own class!");
    }
}
