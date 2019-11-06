package ir.ac.kntu.SAD_fall98.service;

import ir.ac.kntu.SAD_fall98.exception.ClassroomNotFoundException;
import ir.ac.kntu.SAD_fall98.model.Classroom;
import ir.ac.kntu.SAD_fall98.repository.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    final
    ClassroomRepository repository;

    public ClassroomServiceImpl(ClassroomRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Classroom> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Classroom> searchClassroom(String searchItem) {
        List<Classroom> classrooms = repository.searchClassroomByName(searchItem);
        return classrooms;
    }

    @Override
    public Classroom createClassroom(Classroom classroom) {
        Classroom saved = repository.save(classroom);
        return saved;
    }

    @Override
    public void deleteClassroom(long id) {
        Optional<Classroom> optionalClassroom = repository.findById(id);
        Classroom classroom = optionalClassroom.orElseThrow(ClassroomNotFoundException::new);
        repository.delete(classroom);
    }
}
