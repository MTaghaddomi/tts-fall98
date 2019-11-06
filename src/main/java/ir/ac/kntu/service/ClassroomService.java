package ir.ac.kntu.service;

import ir.ac.kntu.model.Classroom;

import java.util.List;

public interface ClassroomService {
    List<Classroom> findAll();

    List<Classroom> searchClassroom(String searchItem);


    Classroom createClassroom(Classroom classroom);

    void deleteClassroom(long id);
}
