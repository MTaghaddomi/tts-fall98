package ir.ac.kntu.repository;

import ir.ac.kntu.model.ExerciseSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseSubmission, Long> {
}
