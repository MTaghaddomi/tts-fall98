package ir.ac.kntu.SAD_fall98.service;

import ir.ac.kntu.SAD_fall98.model.Classroom;

import java.util.List;

public interface ClassroomService {
    List<Classroom> findAll();

    List<Classroom> searchClassroom(String searchItem);


    Classroom createClassroom(Classroom classroom);

    void deleteClassroom(long id);
}
