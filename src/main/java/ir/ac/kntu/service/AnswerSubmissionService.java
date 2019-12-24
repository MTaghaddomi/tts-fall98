package ir.ac.kntu.service;

import ir.ac.kntu.exception.AnswerNotExistedException;
import ir.ac.kntu.exception.NotEnoughAccessLevelException;
import ir.ac.kntu.exception.UserNotExistedException;
import ir.ac.kntu.model.AnswerSubmission;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.model.User;
import ir.ac.kntu.repository.AnswerSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AnswerSubmissionService {
    @Autowired
    private AnswerSubmissionRepository answerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseSubmissionService exerciseService;

    public AnswerSubmission saveAnswer(
            String requesterUsername, long exerciseId, AnswerSubmission answer) {
        User requester = userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new);
        ExerciseSubmission exercise = exerciseService.findExerciseById(exerciseId);

        answer.setCreator(requester);
        answer.setQuestion(exercise);

        answer = answerRepository.save(answer);

        return answer;
    }

    public AnswerSubmission updateAnswer(
            String requesterUsername, long answerId, AnswerSubmission answerSubmission) {
        AnswerSubmission answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotExistedException::new);

        checkAccessLevel(requesterUsername, answer);

        answerRepository.deleteById(answerId);

        answer.setText(answerSubmission.getText());

        answer = answerRepository.save(answer);

        return answer;
    }

    public HttpStatus deleteAnswer(String requesterUsername, long answerId) {
        AnswerSubmission answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotExistedException::new);

        checkAccessLevel(requesterUsername, answer);

        answerRepository.deleteById(answerId);

        return HttpStatus.OK;
    }

    public AnswerSubmission getAnswerInfo(String requesterUsername, long answerId) {
        AnswerSubmission answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotExistedException::new);

        checkAccessLevel(requesterUsername, answer);

        return answer;
    }

    private void checkAccessLevel(String requesterUsername, AnswerSubmission answer){
        if(! answer.getCreator().getUsername().equals(requesterUsername)){
            throw new NotEnoughAccessLevelException();
        }
    }
}
