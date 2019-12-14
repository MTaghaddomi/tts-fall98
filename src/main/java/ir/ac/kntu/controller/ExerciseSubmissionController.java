package ir.ac.kntu.controller;

import ir.ac.kntu.domain.submission.ExerciseSubmissionDetailInfoDTO;
import ir.ac.kntu.domain.submission.ExerciseSubmissionRequestDTO;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.service.ExerciseSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/exercise")
public class ExerciseSubmissionController {
    @Autowired
    private ExerciseSubmissionService exerciseService;

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

        exercise = exerciseService.saveExercise(exercise, classroomName);

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

        ExerciseSubmission exercise = exerciseService.updateExercise
                (exerciseId, exerciseDTO);

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
        return exerciseService.getAnswersSubmitted(exerciseId);
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
}
