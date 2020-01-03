package ir.ac.kntu.controller;

import ir.ac.kntu.domain.submission.ExerciseSubmissionDetailInfoDTO;
import ir.ac.kntu.domain.submission.ExerciseSubmissionRequestDTO;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.service.ExerciseSubmissionService;
import ir.ac.kntu.util.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/exercise")
public class ExerciseSubmissionController {
    @Autowired
    private ExerciseSubmissionService exerciseService;

    @Autowired
    private UserTokenUtil tokenUtil;

    @Deprecated
    @PostMapping("/{classroomName}")
    public ExerciseSubmissionDetailInfoDTO submitExercise(
            @PathVariable String classroomName,
            @RequestBody ExerciseSubmissionRequestDTO exerciseDTO){

        //TODO: use mapper instead
        ExerciseSubmission exercise = new ExerciseSubmission();
        exercise.setSubject(exerciseDTO.getSubject());
        exercise.setDescription(exerciseDTO.getDescription());
        //set creator --> exerciseService
        exercise.setDeadline(exerciseDTO.getDeadline());
        exercise.setLateDeadline(exerciseDTO.getLateDeadline());
        exercise.setAccessLevel(exerciseDTO.getAccessLevel());
        //set classroom --> exerciseService
        //

        String requesterUsername = tokenUtil.token2Username();
        exercise = exerciseService.saveExercise(requesterUsername, exercise, classroomName);

        ExerciseSubmissionDetailInfoDTO result =
                convertExercise2exerciseDetailDTO(exercise);

        return result;
    }

    @PostMapping("/{classroomName}/newApi")
    public ExerciseSubmissionDetailInfoDTO submitExercise(
            @PathVariable String classroomName,
            @RequestParam ExerciseSubmissionRequestDTO exerciseDTO,
            @RequestParam MultipartFile[] files) throws IOException {

        //TODO: use mapper instead
        ExerciseSubmission exercise = new ExerciseSubmission();
        exercise.setSubject(exerciseDTO.getSubject());
        exercise.setDescription(exerciseDTO.getDescription());
        //set creator --> exerciseService
        exercise.setDeadline(exerciseDTO.getDeadline());
        exercise.setLateDeadline(exerciseDTO.getLateDeadline());
        exercise.setAccessLevel(exerciseDTO.getAccessLevel());
        //set classroom --> exerciseService
        //

        String requesterUsername = tokenUtil.token2Username();
        exercise = exerciseService.saveExercise
                (requesterUsername, exercise, classroomName, files);

        ExerciseSubmissionDetailInfoDTO result =
                convertExercise2exerciseDetailDTO(exercise);

        return result;
    }

    @GetMapping("/{id}")
    public ExerciseSubmissionDetailInfoDTO getExerciseDetailInfo(
            @PathVariable(name = "id") Long exerciseId){

        ExerciseSubmission exercise = exerciseService.findExerciseById(exerciseId);

        ExerciseSubmissionDetailInfoDTO result =
                convertExercise2exerciseDetailDTO(exercise);

        return result;
    }

    @PutMapping("/{id}")
    public ExerciseSubmissionDetailInfoDTO updateExercise(
            @PathVariable(name = "id") Long exerciseId,
            @RequestBody ExerciseSubmissionRequestDTO exerciseDTO){

        String requesterUsername = tokenUtil.token2Username();

        ExerciseSubmission exercise = exerciseService.updateExercise
                (requesterUsername, exerciseId, exerciseDTO);

        ExerciseSubmissionDetailInfoDTO result =
                convertExercise2exerciseDetailDTO(exercise);

        return result;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteExercise(@PathVariable(name = "id") Long exerciseId){
        return exerciseService.deleteExerciseById(exerciseId);
    }

    @GetMapping("/{id}/answers")
    public List<String> getSubmittedAnswers(@PathVariable(name = "id") Long exerciseId){
        String requesterUsername = tokenUtil.token2Username();
        return exerciseService.getAnswersSubmitted(requesterUsername, exerciseId);
    }


    private ExerciseSubmissionDetailInfoDTO convertExercise2exerciseDetailDTO
            (ExerciseSubmission exercise){
        //TODO: use mapper instead
        ExerciseSubmissionDetailInfoDTO result = new ExerciseSubmissionDetailInfoDTO();
        result.setId(exercise.getId());
        result.setSubject(exercise.getSubject());
        result.setDescription(exercise.getDescription());
        result.setDeadline(exercise.getDeadline());
        result.setLateDeadline(exercise.getLateDeadline());
        result.setAccessLevel(exercise.getAccessLevel());
        result.setFileUrls(exercise.getFileUrls());
        //

        return result;
    }


    @GetMapping("/{id}/newApi")
    public void sendFileToUser(
            @PathVariable(name = "id") Long exerciseId,
            HttpServletResponse response) throws IOException {

        String requesterUsername = tokenUtil.token2Username();

        exerciseService.copyFileTo(requesterUsername, response.getOutputStream(), exerciseId);

        response.addHeader("Content-Disposition",
                "attachment; filename=" + exerciseId);
    }
}
