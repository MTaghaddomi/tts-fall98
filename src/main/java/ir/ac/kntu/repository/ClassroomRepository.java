package ir.ac.kntu.repository;

import ir.ac.kntu.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
//    @Query("SELECT c FROM Classroom c WHERE c.name LIKE :searchItem")
//    List<Classroom> searchClassroomByName(@Param("searchItem") String searchItem);

    List<Classroom> findAllClassroomsByNameContains(@NotBlank String name);

    List<Classroom> findClassroomsByLessonName(String lessonName);

    Optional<Classroom> findClassroomByName(String name);

//    void deleteByName(@NotBlank String name);










}
