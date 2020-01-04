package ir.ac.kntu.service;

import ir.ac.kntu.exception.AnswerNotExistedException;
import ir.ac.kntu.model.AnswerSubmission;
import ir.ac.kntu.repository.AnswerSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Morteza Taghaddomi
 */
@Service
public class GradeExerciseService {
    @Autowired
    AnswerSubmissionRepository answerSubmissionRepository;

    public void gradeExercise(AnswerSubmission answerSubmission, long exerciseId, long answerId) {
        Optional<AnswerSubmission> optionalAnswerSubmission = answerSubmissionRepository.findById(answerSubmission.getId());
        AnswerSubmission submission = optionalAnswerSubmission.orElseThrow(AnswerNotExistedException::new);

        submission.setGrade(answerSubmission.getGrade());
        AnswerSubmission gradedAnswerSubmission = answerSubmissionRepository.save(submission);


    }
}
