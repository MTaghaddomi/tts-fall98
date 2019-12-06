package ir.ac.kntu.service;

import ir.ac.kntu.exception.ClassroomNotFoundException;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {
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

    public void deleteClassroom(String name) {
        Optional<Classroom> optionalClassroom = repository.findClassroomByName(name);
        Classroom classroom = optionalClassroom.orElseThrow(ClassroomNotFoundException::new);
        repository.delete(classroom);
    }

    private List<Classroom> findByLessonName(String lessonName) {
        return repository.findClassroomsByLessonName(lessonName);
    }

    public Classroom updateClassroom(Classroom classroom) {
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

}
