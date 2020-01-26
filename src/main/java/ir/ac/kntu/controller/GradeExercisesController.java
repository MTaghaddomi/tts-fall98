package ir.ac.kntu.controller;

import ir.ac.kntu.domain.submission.GradeAnswerInfoRequestDTO;
import ir.ac.kntu.mapper.GradeAnswerInfoMapper;
import ir.ac.kntu.model.AnswerSubmission;
import ir.ac.kntu.service.GradeExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Morteza Taghaddomi
 */
@RestController
@RequestMapping("/classrooms/exercises")
public class GradeExercisesController {
    @Autowired
    private GradeExerciseService service;

    @Autowired
    private GradeAnswerInfoMapper gradeMapper;


    @PostMapping("/{exerciseId}/answer/{answerId}")
    public void gradeExercise(@PathVariable long exerciseId, @PathVariable long answerId,
                              @RequestBody GradeAnswerInfoRequestDTO gradeDto) {
        AnswerSubmission answerSubmission = gradeMapper.convertToGradeAnswerRequestDto(gradeDto);
        service.gradeExercise(answerSubmission, exerciseId, answerId);
    }

    @GetMapping("/{exerciseId}/answer/{answerId}")
    public double getGradeExercise(@PathVariable long exerciseId, @PathVariable long answerId) {
        return service.findExerciseGrade(exerciseId, answerId);

    }

}
