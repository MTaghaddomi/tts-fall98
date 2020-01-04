package ir.ac.kntu.repository;

import ir.ac.kntu.model.AnswerSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerSubmissionRepository extends JpaRepository<AnswerSubmission, Long> {
    Optional<AnswerSubmission> findByIdAndQuestionId(long id, long questionId);

    List<AnswerSubmission> findAllByQuestionId(long questionId);
}
