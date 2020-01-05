package ir.ac.kntu.service;

import ir.ac.kntu.exception.AnswerNotExistedException;
import ir.ac.kntu.exception.NotEnoughAccessLevelException;
import ir.ac.kntu.exception.UserNotExistedException;
import ir.ac.kntu.model.AnswerSubmission;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.model.User;
import ir.ac.kntu.repository.AnswerSubmissionRepository;
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
public class AnswerSubmissionService {
    private final static String FILE_SEPARATOR = File.separator;

    @Value("${spring.servlet.multipart.location}")
    private String prefixAddress;

    @Autowired
    private AnswerSubmissionRepository answerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseSubmissionService exerciseService;

    @Autowired
    private FileTransitionUtil fileUtil;

    @Deprecated
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

    public AnswerSubmission saveAnswer(
            String requesterUsername, long exerciseId, AnswerSubmission answer,
            MultipartFile[] files) throws IOException {

        User requester = userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new);
        ExerciseSubmission exercise = exerciseService.findExerciseById(exerciseId);

        answer.setCreator(requester);
        answer.setQuestion(exercise);

        if(files != null){
            String classId = answer.getQuestion().getClassroom().getId() + "";
            saveFiles(files, requesterUsername, classId, answer);
        }

        answer = answerRepository.save(answer);

        return answer;
    }

    public AnswerSubmission saveAnswer(
            String requesterUsername, long exerciseId, AnswerSubmission answer,
            MultipartFile file) throws IOException {

        User requester = userService.findByUsername(requesterUsername)
                .orElseThrow(UserNotExistedException::new);
        ExerciseSubmission exercise = exerciseService.findExerciseById(exerciseId);

        answer.setCreator(requester);
        answer.setQuestion(exercise);

        if(file != null){
            System.out.println("answer service, before save");
            String classId = answer.getQuestion().getClassroom().getId() + "";
            saveFile(file, requesterUsername, classId, answer, exercise);
            System.out.println("answer service, before save");
        }

        answer = answerRepository.save(answer);

        return answer;
    }

    private void saveFiles(MultipartFile[] files, String userId, String classId,
                           AnswerSubmission answer) throws IOException {
        if(files == null) {
            return;
        }

        for(MultipartFile file : files){
            String folderAddress = getRootFolderOfFiles(userId, classId);

            String fileId = fileUtil.saveFile(file, folderAddress);

            String fileAddress = folderAddress + FILE_SEPARATOR + fileId;
            answer.addFileUrl(fileAddress);
        }
    }

    private void saveFile(MultipartFile file, String userId, String classId,
                           AnswerSubmission answer, ExerciseSubmission exercise) throws IOException {
        if(file == null) {
            return;
        }

        String folderAddress = getRootFolderOfFiles(userId, classId);

        String fileId = fileUtil.saveFile(file, folderAddress);

        String fileAddress = folderAddress + FILE_SEPARATOR + fileId;
        System.out.println("answer service, save method, line 128");
        System.out.println("before, answer urls: " + answer.getFileUrls());
        answer.addFileUrl(fileAddress);
        System.out.println("after, answer urls: " + answer.getFileUrls());

        System.out.println("answer service, save method, line 128");
        System.out.println("before, exercise's answer urls: " + exercise.getAnswersUrl());
        exercise.addAnswerUrl(fileAddress);
        System.out.println("after, exercise's answer urls: " + exercise.getAnswersUrl());
        System.out.println("answer service, save method, line 128");
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

    public void copyFileTo(String requesterUsername, OutputStream outputStream,
                           long answerId) throws IOException {
        AnswerSubmission answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotExistedException::new);

        String classId = answer.getQuestion().getClassroom().getId() + "";

        String folderAddress = getRootFolderOfFiles(requesterUsername, classId);

        String fileAddress = folderAddress + FILE_SEPARATOR + answerId;

        fileUtil.copyFile(fileAddress, outputStream);
    }

    private String getRootFolderOfFiles(String userId, String classId){
        String rootFolderAddress = prefixAddress + FILE_SEPARATOR +
                "users" + FILE_SEPARATOR + userId + FILE_SEPARATOR +
                "classes" + FILE_SEPARATOR + classId + FILE_SEPARATOR + "answers";

        return rootFolderAddress;
    }

    public List<AnswerSubmission> findAllAnswerSubmissionByExerciseId(long exerciseId) {
        List<AnswerSubmission> answerSubmissions = answerRepository.findAllByQuestionId(exerciseId);
        return answerSubmissions;
    }



}
