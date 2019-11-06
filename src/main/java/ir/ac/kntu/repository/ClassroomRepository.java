package ir.ac.kntu.repository;

import ir.ac.kntu.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    @Query("SELECT c FROM Classroom c WHERE c.name LIKE :searchItem")
    List<Classroom> searchClassroomByName(@Param("searchItem") String searchItem);


}
