package ir.ac.kntu.repository;

import ir.ac.kntu.model.AnswerSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerSubmissionRepository extends JpaRepository<AnswerSubmission, Long> {

}
