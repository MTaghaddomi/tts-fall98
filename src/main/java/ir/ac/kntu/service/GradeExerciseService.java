package ir.ac.kntu.service;

import ir.ac.kntu.exception.AnswerNotExistedException;
import ir.ac.kntu.model.AnswerSubmission;
import ir.ac.kntu.repository.AnswerSubmissionRepository;
import ir.ac.kntu.repository.ExerciseRepository;
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

    @Autowired
    ExerciseRepository exerciseRepository;

    public AnswerSubmission gradeExercise(AnswerSubmission answerSubmission, long exerciseId, long answerId) {
        Optional<AnswerSubmission> optionalAnswerSubmission = answerSubmissionRepository.findById(answerSubmission.getId());
        AnswerSubmission submission = optionalAnswerSubmission.orElseThrow(AnswerNotExistedException::new);

        submission.setGrade(answerSubmission.getGrade());
        AnswerSubmission gradedAnswerSubmission = answerSubmissionRepository.save(submission);

        return gradedAnswerSubmission;

    }

    public double findExerciseGrade(long exerciseId, long answerId) {
        Optional<AnswerSubmission> answerSubmissionOptional = answerSubmissionRepository.findById(answerId);
        AnswerSubmission answerSubmission = answerSubmissionOptional.orElseThrow(AnswerNotExistedException::new);

        return answerSubmission.getGrade();
    }
}
