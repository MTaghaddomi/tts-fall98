package ir.ac.kntu.controller;

import ir.ac.kntu.domain.submission.AnswerSubmissionInfoDTO;
import ir.ac.kntu.domain.submission.AnswerSubmissionRequestDTO;
import ir.ac.kntu.model.AnswerSubmission;
import ir.ac.kntu.service.AnswerSubmissionService;
import ir.ac.kntu.util.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercise/answer")
public class AnswerSubmissionController {
    @Autowired
    private AnswerSubmissionService answerService;

    @Autowired
    private UserTokenUtil tokenUtil;

    @PostMapping("/{exerciseId}")
    public AnswerSubmissionInfoDTO submitAnswer(
            @PathVariable Long exerciseId,
            @RequestBody AnswerSubmissionRequestDTO answerSubmissionRequestDTO){

        AnswerSubmission answerSubmission = convertAnswerRequestDTO2Answer
                (answerSubmissionRequestDTO);

        String requesterUsername = tokenUtil.token2Username();
        answerSubmission = answerService.saveAnswer(requesterUsername, exerciseId, answerSubmission);

        return convertAnswer2answerInfoDTO(answerSubmission);
    }

    @PutMapping("/{answerId}")
    public AnswerSubmissionInfoDTO updateAnswer(
            @PathVariable Long answerId,
            @RequestBody AnswerSubmissionRequestDTO answerSubmissionRequestDTO){

        AnswerSubmission answerSubmission = convertAnswerRequestDTO2Answer
                (answerSubmissionRequestDTO);

        String requesterUsername = tokenUtil.token2Username();
        answerSubmission = answerService.updateAnswer(requesterUsername, answerId, answerSubmission);

        return convertAnswer2answerInfoDTO(answerSubmission);

    }

    @DeleteMapping("/{answerId}")
    public HttpStatus deleteAnswer(@PathVariable Long answerId){
        String requesterUsername = tokenUtil.token2Username();
        return answerService.deleteAnswer(requesterUsername, answerId);
    }

    @GetMapping("/{answerId}")
    public AnswerSubmissionInfoDTO getAnswerInfo(@PathVariable Long answerId){
        String requesterUsername = tokenUtil.token2Username();
        AnswerSubmission answer = answerService.getAnswerInfo(requesterUsername, answerId);

        return convertAnswer2answerInfoDTO(answer);
    }


    private AnswerSubmissionInfoDTO convertAnswer2answerInfoDTO(
            AnswerSubmission answerSubmission){
        AnswerSubmissionInfoDTO result = new AnswerSubmissionInfoDTO(
                answerSubmission.getId(), answerSubmission.getText(),
                answerSubmission.getFileUrls());
        return result;
    }

    private AnswerSubmission convertAnswerRequestDTO2Answer(
            AnswerSubmissionRequestDTO answerDTO){
        //TODO: use mapper instead
        AnswerSubmission result = new AnswerSubmission();
        result.setText(answerDTO.getText());
        //set creator --> answerService
        //set question --> answerService
        //

        return result;
    }
}
