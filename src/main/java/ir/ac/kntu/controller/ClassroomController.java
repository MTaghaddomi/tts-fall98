package ir.ac.kntu.controller;

import ir.ac.kntu.domain.classroom.*;
import ir.ac.kntu.domain.submission.ExerciseSubmissionGeneralInfoDTO;
import ir.ac.kntu.domain.user.UserInfoDTO;
import ir.ac.kntu.mapper.ClassroomMapper;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.model.Lesson;
import ir.ac.kntu.model.User;
import ir.ac.kntu.service.ClassroomService;
import ir.ac.kntu.util.UserTokenUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/classrooms")
public class ClassroomController {
    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserTokenUtil tokenUtil;

    private ClassroomMapper mapper = Mappers.getMapper(ClassroomMapper.class);

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<SearchClassroomDto> findAllClassrooms() {
        List<Classroom> classrooms = classroomService.findAll();
        return classrooms.stream()
                .map(mapper::convertSearchClassroomDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<RegisterClassroomRequestDto> searchClassroom(@RequestParam(required = false) String name,
                                                             @RequestParam(name = "lesson", required = false) String lessonName) {
        List<Classroom> classrooms = classroomService.searchClassroom(name, lessonName);

        return classrooms.stream()
                .map(mapper::convertClassroomRegisterDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ClassroomGeneralInfoDTO createClassroom(
            @RequestBody RegisterClassroomRequestDto registerClassDto) {

        //TODO: use mapper instead
        Classroom classroom = new Classroom();
        classroom.setName(registerClassDto.getName());
        classroom.setDescription(registerClassDto.getDescription());
        Lesson lesson = new Lesson();
        lesson.setName(registerClassDto.getLesson().getName());
//        lesson.setDescription(registerClassDto.getLesson().getDescription());
        classroom.setLesson(lesson);
        User requester = tokenUtil.token2User();
        classroom.setTeacher(requester);
        //

        classroom = classroomService.createClassroom(classroom);

        ClassroomGeneralInfoDTO result = convertClass2ClassGeneralInfoDTO(classroom);

        return result;
    }

    @PutMapping("/{classroomName}")
    @ResponseStatus(value = HttpStatus.OK)
    public ClassroomGeneralInfoDTO updateClassroom(
            @PathVariable String classroomName,
            @RequestBody EditableClassroomRequestDto editClassDto) {

        //TODO: use mapper instead
        Classroom editClass = new Classroom();
        editClass.setName(editClassDto.getName());
        editClass.setDescription(editClassDto.getDescription());
        Lesson lesson = new Lesson();
        lesson.setName(editClassDto.getLesson().getName());
//        lesson.setDescription(editClassDto.getLesson().getDescription());
        editClass.setLesson(lesson);
        if(editClassDto.getAssistant() != null){
            for(UserInfoDTO userInfoDTO : editClassDto.getAssistant()){
                User user = new User();
                user.setFirstName(userInfoDTO.getFirstName());
                user.setLastName(userInfoDTO.getLastName());
                user.setUsername(userInfoDTO.getEmail());
                user.setEmail(userInfoDTO.getEmail());
                editClass.addAssistant(user);
            }
        }
        if(editClassDto.getStudents() != null){
            for(UserInfoDTO userInfoDTO : editClassDto.getStudents()){
                User user = new User();
                user.setFirstName(userInfoDTO.getFirstName());
                user.setLastName(userInfoDTO.getLastName());
                user.setUsername(userInfoDTO.getEmail());
                user.setEmail(userInfoDTO.getEmail());
                editClass.addStudent(user);
            }
        }
        //

        String requesterUsername = tokenUtil.token2Username();
        Classroom classroom = classroomService.updateClassroom
                (requesterUsername, classroomName, editClass);

        ClassroomGeneralInfoDTO result = convertClass2ClassGeneralInfoDTO(classroom);

        return result;
    }

    @DeleteMapping("/{classroomName}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteClassroom(@PathVariable String classroomName) {
        String requesterUsername = tokenUtil.token2Username();
        classroomService.deleteClassroom(requesterUsername, classroomName);
    }

    @GetMapping("/{classroomName}")
    public ClassroomDetailInfoDTO getClassroomDetailInfo(
            @PathVariable String classroomName){
        String requesterUsername = tokenUtil.token2Username();
        Classroom classroom = classroomService
                .getClassroomDetailForRequester(requesterUsername, classroomName);

        //TODO: use mapper instead
        ClassroomDetailInfoDTO classroomInfoDTO = new ClassroomDetailInfoDTO();
        classroomInfoDTO.setName(classroom.getName());
        classroomInfoDTO.setDescription(classroom.getDescription());
        classroomInfoDTO.setLesson(new LessonDto(classroom.getLesson().getName()//,
//                classroom.getLesson().getDescription()));
        ));
        User teacher = classroom.getTeacher();
        classroomInfoDTO.setTeacherInfo(new UserInfoDTO(teacher.getFirstName(),
                teacher.getLastName(), teacher.getEmail()));
        List<User> students = classroom.getStudents();
        List<UserInfoDTO> studentsInfo;
        if(students == null || students.isEmpty()){
            studentsInfo = new ArrayList<>();
        }else{
            studentsInfo = new ArrayList<>();
            for(User s : students){
                studentsInfo.add(new UserInfoDTO
                        (s.getFirstName(), s.getLastName(), s.getEmail()));
            }
        }
        classroomInfoDTO.setStudentsInfo(studentsInfo);
        //

        if (requesterUsername.equals(teacher.getUsername())) {
            classroomInfoDTO.setRole(UserRole.teacher);
        } else {
            classroomInfoDTO.setRole(UserRole.student);
        }

        return classroomInfoDTO;
    }

    @PostMapping("/join/{classroomName}")
    public ClassroomGeneralInfoDTO joinClass(
            @PathVariable String classroomName){

        String requesterUsername = tokenUtil.token2Username();
        Classroom classroom = classroomService.joinClass(requesterUsername, classroomName);

        ClassroomGeneralInfoDTO result = convertClass2ClassGeneralInfoDTO(classroom);

        return result;
    }

    @GetMapping("/{classroomName}/exercises")
    public List<ExerciseSubmission> getClassroomExercises(
            @PathVariable String classroomName){
        String requesterUsername = tokenUtil.token2Username();
        List<ExerciseSubmission> exercises = classroomService
                .getExercises(requesterUsername, classroomName);

        List<ExerciseSubmissionGeneralInfoDTO> exercisesInfo = new ArrayList<>();
        //TODO: use mapper instead
        if(exercises == null || exercises.isEmpty()){
            exercises = null;
        }else{
            for(ExerciseSubmission exercise : exercises){
                exercisesInfo.add(new ExerciseSubmissionGeneralInfoDTO
                        (exercise.getId(), exercise.getSubject()));
            }
        }
        //

        return exercises;
    }

    private ClassroomGeneralInfoDTO convertClass2ClassGeneralInfoDTO
            (Classroom classroom){
        //TODO: use mapper instead
        ClassroomGeneralInfoDTO result = new ClassroomGeneralInfoDTO();
        result.setName(classroom.getName());
        result.setLesson(classroom.getLesson().getName());
        result.setTeacher(classroom.getTeacher().getFirstName() + " " +
                classroom.getTeacher().getLastName());
        //

        return result;
    }
}
