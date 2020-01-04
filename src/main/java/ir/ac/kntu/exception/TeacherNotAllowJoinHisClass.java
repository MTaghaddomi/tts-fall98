package ir.ac.kntu.exception;

/**
 * @author Morteza Taghaddomi
 */
public class TeacherNotAllowJoinHisClass extends RuntimeException {
    public TeacherNotAllowJoinHisClass() {
        this("Teacher can't join his class!");
    }

    public TeacherNotAllowJoinHisClass(String message) {
        super(message);
    }
}
