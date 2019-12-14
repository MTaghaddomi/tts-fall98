package ir.ac.kntu.service;

import ir.ac.kntu.exception.ClassroomNotFoundException;
import ir.ac.kntu.exception.NotEnoughAccessLevelException;
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
import java.util.Optional;

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
        String requesterUsername = UserService.getUsernameOfRequester();
        User requester = userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new);
        classroom.setTeacher(requester);

        return repository.save(classroom);
    }

    public void deleteClassroom(String name) {
        Classroom classroom = repository.findClassroomByName(name)
                .orElseThrow(ClassroomNotFoundException::new);

        String requesterUsername = UserService.getUsernameOfRequester();
        if(! classroom.getTeacher().getUsername().equals(requesterUsername)){
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

    public Classroom updateClassroom(String className, Classroom editClass) {
        Classroom classroom = findByClassroomName(className);

        String requesterUsername = UserService.getUsernameOfRequester();
        if(! isTeacherIn(requesterUsername, classroom)){
            throw new NotEnoughAccessLevelException();
        }

        repository.delete(classroom);

        classroom.setName(editClass.getName());
        classroom.setDescription(editClass.getDescription());
        Lesson lesson = classroom.getLesson();
        lesson.setName(editClass.getLesson().getName());
        classroom.setAssistant(editClass.getAssistant());
        classroom.setStudents(editClass.getStudents());

        return repository.save(classroom);
    }

    public Classroom findByClassroomName(String classroomName) {
        Optional<Classroom> optionalClass = repository.findClassroomByName(classroomName);
        return optionalClass.orElseThrow(ClassroomNotFoundException::new);
    }

    public List<Classroom> searchClassroom(String name, String lessonName) {
        if (name != null) {
            return searchClassroomByNameContains(name);
        } else {
            return findByLessonName(lessonName);
        }
    }

    public Classroom getClassroomDetailForRequester(final String classroomName){
        Classroom classroom = findByClassroomName(classroomName);
        String requesterUsername = UserService.getUsernameOfRequester();
        if(isTeacherIn(requesterUsername, classroom) ||
                isAssistantIn(requesterUsername, classroom)){
            return classroom;
        }else if(isStudentIn(requesterUsername, classroom)){
            return classroom.withStudents(null);
        }else{
            return classroom.withStudents(null).withAssistant(null);
        }
    }

    public List<ExerciseSubmission> getExercises(final String classroomName){
        //TODO: refactor
        Classroom classroom =findByClassroomName(classroomName);
        String requesterUsername = UserService.getUsernameOfRequester();

        List<ExerciseSubmission> allExercises = classroom.getExercises();
        List<ExerciseSubmission> userExercise = new ArrayList<>();
        boolean isOrganizer = isTeacherIn(requesterUsername, classroom) ||
                isAssistantIn(requesterUsername, classroom);
        for (ExerciseSubmission exercise : allExercises) {
            switch (exercise.getAccessLevel()){
                case ALL_STUDENTS:
                    userExercise.add(exercise);
                    break;
                case ONLY_CREATOR:
                    if(exercise.getCreator().getUsername().equals(requesterUsername)){
                        userExercise.add(exercise);
                    }
                    break;

                case CLASS_ORGANIZER:
                    if(isOrganizer){
                        userExercise.add(exercise);
                    }
                    break;
            }
        }

        return userExercise;
    }

    public boolean isStudentIn(final String requesterUsername, final Classroom classroom){
        return classroom.getStudents().stream()
                .filter(student -> student.getUsername().equals(requesterUsername))
                .count()>0;
    }

    public boolean isTeacherIn(final String requesterUsername, final Classroom classroom){
        return classroom.getTeacher().getUsername().equals(requesterUsername);
    }

    public boolean isAssistantIn(final String requesterUsername, final Classroom classroom){
        return classroom.getAssistant().stream()
                .filter(student -> student.getUsername().equals(requesterUsername))
                .count()>0;
    }

    public Classroom joinClass(String classroomName) {
        String requesterUsername = UserService.getUsernameOfRequester();
        User requester = userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new);

        Classroom classroom = findByClassroomName(classroomName);
        classroom.getStudents().add(requester);

        return classroom;
    }
}
