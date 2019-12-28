package ir.ac.kntu.service;

import ir.ac.kntu.exception.ClassroomNotFoundException;
import ir.ac.kntu.exception.NotEnoughAccessLevelException;
import ir.ac.kntu.exception.TeacherNotAllowJoinHisClass;
import ir.ac.kntu.exception.UserNotExistedException;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.model.Lesson;
import ir.ac.kntu.model.User;
import ir.ac.kntu.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomService {
    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomRepository repository;

    public ClassroomService(ClassroomRepository repository) {
        this.repository = repository;
    }

    public List<Classroom> findAll() {
        return repository.findAll();
    }

    private List<Classroom> searchClassroomByNameContains(String searchItem) {
        return repository.findAllClassroomsByNameContains(searchItem);
    }

    public Classroom createClassroom(Classroom classroom) {
        return repository.save(classroom);
    }

    public void deleteClassroom(String requesterUsername, String className) {
        Classroom classroom = repository.findClassroomByName(className)
                .orElseThrow(ClassroomNotFoundException::new);

        if (!classroom.getTeacher().getUsername().equals(requesterUsername)) {
            throw new NotEnoughAccessLevelException();
        }

        repository.delete(classroom);
    }

    private List<Classroom> findByLessonName(String lessonName) {
        return repository.findClassroomsByLessonName(lessonName);
    }

    /*public Classroom updateClassroom(Classroom classroom) {
        return repository.save(classroom);
    }*/

    public Classroom updateClassroom(String requesterUsername,
                                     String className, Classroom editClass) {
        Classroom classroom = findByClassroomName(className);

        if (!isTeacherIn(requesterUsername, classroom)) {
            throw new NotEnoughAccessLevelException();
        }

        repository.delete(classroom);

        classroom.setName(editClass.getName());
        classroom.setDescription(editClass.getDescription());
        Lesson lesson = classroom.getLesson();
        lesson.setName(editClass.getLesson().getName());
//        lesson.setDescription(editClass.getLesson().getDescription());

        classroom.removeAllAssistants();
        for (User assistant : editClass.getAssistants()) {
            classroom.addAssistant(assistant);
        }

        classroom.removeAllStudents();
        for (User student : editClass.getStudents()) {
            classroom.addStudent(student);
        }

        return repository.save(classroom);
    }

    public Classroom findByClassroomName(String classroomName) {
        return repository.findClassroomByName(classroomName)
                .orElseThrow(ClassroomNotFoundException::new);
    }

    public List<Classroom> searchClassroom(String name, String lessonName) {
        if (name != null) {
            return searchClassroomByNameContains(name);
        } else if (lessonName != null) {
            return findByLessonName(lessonName);
        } else {
            return new ArrayList<>();
        }
    }

    public Classroom getClassroomDetailForRequester(
            String requesterUsername, final String classroomName) {
        Classroom classroom = findByClassroomName(classroomName);

        if (isTeacherIn(requesterUsername, classroom) ||
                isAssistantIn(requesterUsername, classroom)) {
            return classroom;
        } else if (isStudentIn(requesterUsername, classroom)) {
            return classroom.withStudents(null);
        } else {
            return classroom.withStudents(null).withAssistants(null);
        }
    }

    public List<ExerciseSubmission> getExercises(
            String requesterUsername, final String classroomName) {
        Classroom classroom = findByClassroomName(classroomName);

        List<ExerciseSubmission> allExercises = classroom.getExercises();
        List<ExerciseSubmission> userExercise = new ArrayList<>();
        allExercises.stream().forEach(exercise -> {
            switch (exercise.getAccessLevel()) {
                case ALL_STUDENTS:
                    userExercise.add(exercise);
                    break;
                case ONLY_CREATOR:
                    if (exercise.isCreator(requesterUsername)) {
                        userExercise.add(exercise);
                    }
                    break;
                case CLASS_ORGANIZER:
                    if (isOrganizer(requesterUsername, classroom)) {
                        userExercise.add(exercise);
                    }
                    break;
            }
        });

        return userExercise;
    }

    public boolean isStudentIn(final String requesterUsername, final Classroom classroom) {
        return classroom.getStudents() != null && classroom.getStudents().stream()
                .filter(student -> student.getUsername().equals(requesterUsername))
                .count() > 0;
    }

    public boolean isTeacherIn(final String requesterUsername, final Classroom classroom) {
        return classroom.getTeacher() != null && classroom.getTeacher().getUsername().equals(requesterUsername);
    }

    public boolean isAssistantIn(final String requesterUsername, final Classroom classroom) {
        return classroom.getAssistants() != null && classroom.getAssistants().stream()
                .filter(student -> student.getUsername().equals(requesterUsername))
                .count() > 0;
    }

    private boolean isOrganizer(final String requesterUsername,
                                final Classroom classroom) {
        return isTeacherIn(requesterUsername, classroom) ||
                isAssistantIn(requesterUsername, classroom);
    }

    public Classroom joinClass(String requesterUsername, String classroomName) {
        User requester = userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new);


        Classroom classroom = findByClassroomName(classroomName);
        if (requesterUsername.equals(classroom.getTeacher().getUsername())) {
            throw new TeacherNotAllowJoinHisClass();
        }

        classroom.addStudent(requester);
        repository.save(classroom);

        return classroom;
    }
}
