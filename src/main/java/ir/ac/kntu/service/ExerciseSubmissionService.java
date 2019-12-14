package ir.ac.kntu.service;

import ir.ac.kntu.domain.submission.ExerciseSubmissionRequestDTO;
import ir.ac.kntu.exception.ExerciseNotExistedException;
import ir.ac.kntu.exception.NotEnoughAccessLevelException;
import ir.ac.kntu.exception.UserNotExistedException;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseSubmissionService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomService classroomService;


    public ExerciseSubmission saveExercise(ExerciseSubmission exercise, String className) {
        String requesterUsername = userService.getUsernameOfRequester();

        exercise.setCreator(userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new));
        exercise.setClassroom(classroomService.findByClassroomName(className));

        exercise = exerciseRepository.save(exercise);

        return exercise;
    }

    public ExerciseSubmission findExerciseById(long exerciseId) {
        return exerciseRepository.findById(exerciseId)
                .orElseThrow(ExerciseNotExistedException::new);
    }

    public ExerciseSubmission updateExercise(long exerciseId,
                                             ExerciseSubmissionRequestDTO exerciseDTO) {

        ExerciseSubmission exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(ExerciseNotExistedException::new);

        exerciseRepository.deleteById(exerciseId);

        exercise.setSubject(exerciseDTO.getSubject());
        exercise.setDescription(exerciseDTO.getSubject());
        exercise.setDeadline(exerciseDTO.getDeadline());
        exercise.setLateDeadline(exerciseDTO.getLateDeadline());
        exercise.setAccessLevel(exerciseDTO.getAccessLevel());

        exercise = exerciseRepository.save(exercise);

        return exercise;
    }

    public HttpStatus deleteExerciseById(long exerciseId) {
        if(! exerciseRepository.existsById(exerciseId)){
            throw new ExerciseNotExistedException();
        }

        exerciseRepository.deleteById(exerciseId);

        return HttpStatus.OK;
    }

    public List<String> getAnswersSubmitted(long exerciseId) {
        String requesterUsername = userService.getUsernameOfRequester();
        ExerciseSubmission exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(ExerciseNotExistedException::new);
        Classroom classroom = exercise.getClassroom();

        if(classroomService.isTeacherIn(requesterUsername, classroom) ||
                classroomService.isAssistantIn(requesterUsername, classroom)){

            return exercise.getAnswersUrl();
        }else{
            throw new NotEnoughAccessLevelException();
        }
    }
}
