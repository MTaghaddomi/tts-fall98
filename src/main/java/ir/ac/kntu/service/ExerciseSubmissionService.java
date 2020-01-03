package ir.ac.kntu.service;

import ir.ac.kntu.domain.submission.ExerciseSubmissionRequestDTO;
import ir.ac.kntu.exception.ExerciseNotExistedException;
import ir.ac.kntu.exception.NotEnoughAccessLevelException;
import ir.ac.kntu.exception.UserNotExistedException;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.repository.ExerciseRepository;
import ir.ac.kntu.util.FileTransitionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExerciseSubmissionService {
    private final static String FILE_SEPARATOR = File.separator;

    @Value("${spring.servlet.multipart.location}")
    private String prefixAddress;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private FileTransitionUtil fileUtil;

    @Deprecated
    public ExerciseSubmission saveExercise(
            String requesterUsername, ExerciseSubmission exercise, String className) {

        exercise.setCreator(userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new));
        exercise.setClassroom(classroomService.findByClassroomName(className));

        exercise = exerciseRepository.save(exercise);

        return exercise;
    }

    public ExerciseSubmission saveExercise(
            String requesterUsername, ExerciseSubmission exercise,
            String className, MultipartFile[] files) throws IOException {

        exercise.setCreator(userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new));
        exercise.setClassroom(classroomService.findByClassroomName(className));

        if(files != null){
            String classId = exercise.getClassroom().getId() + "";
            saveFiles(files, requesterUsername, classId, exercise);
        }

        exercise = exerciseRepository.save(exercise);

        return exercise;
    }

    private void saveFiles(MultipartFile[] files, String userId, String classId,
                           ExerciseSubmission exercise) throws IOException {
        if(files == null){
            return;
        }
        for(MultipartFile file : files){
            String folderAddress = getRootFolderOfFiles(userId, classId);

            String fileId = fileUtil.saveFile(file, folderAddress);

            String fileAddress = folderAddress + FILE_SEPARATOR + fileId;
            exercise.addFileUrl(fileAddress);
        }
    }

    public ExerciseSubmission findExerciseById(long exerciseId) {
        return exerciseRepository.findById(exerciseId)
                .orElseThrow(ExerciseNotExistedException::new);
    }

    public ExerciseSubmission updateExercise(
            String requesterUsername, long exerciseId,
            ExerciseSubmissionRequestDTO exerciseDTO) {

        ExerciseSubmission exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(ExerciseNotExistedException::new);

        if(! exercise.isCreator(requesterUsername)){
            throw new NotEnoughAccessLevelException();
        }

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

    public List<String> getAnswersSubmitted(String requesterUsername, long exerciseId) {
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

    public void copyFileTo(String requesterUsername, OutputStream outputStream,
                           long exerciseId) throws IOException {
        ExerciseSubmission exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(ExerciseNotExistedException::new);

        String classId = exercise.getClassroom().getId() + "";

        String folderAddress = getRootFolderOfFiles(requesterUsername, classId);

        String fileAddress = folderAddress + FILE_SEPARATOR + exerciseId;

        fileUtil.copyFile(fileAddress, outputStream);
    }

    private String getRootFolderOfFiles(String userId, String classId){
        String rootFolderAddress = prefixAddress + FILE_SEPARATOR +
                "users" + FILE_SEPARATOR + userId + FILE_SEPARATOR +
                "classes" + FILE_SEPARATOR + classId + FILE_SEPARATOR + "exercises";

        return rootFolderAddress;
    }
}
