package ir.ac.kntu.controller;

import ir.ac.kntu.domain.classroom.*;
import ir.ac.kntu.domain.submission.ExerciseSubmissionGeneralInfoDTO;
import ir.ac.kntu.domain.user.UserInfoDTO;
import ir.ac.kntu.mapper.ClassroomMapper;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.model.User;
import ir.ac.kntu.service.ClassroomService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    private ClassroomService classroomService;

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
    public RegisterClassroomResponseDto createClassroom(@RequestBody RegisterClassroomRequestDto registerClassDto) {
        // Todo validate user , get User from database
        Classroom classroom = mapper.convertClassroomRegisterDto(registerClassDto);
        classroomService.createClassroom(classroom);
        return new RegisterClassroomResponseDto();
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void updateClassroom(@RequestBody EditableClassroomRequestDto editClassDto) {
        Classroom classroom = mapper.convertEditableClassroomDto(editClassDto);
        classroomService.updateClassroom(classroom);
    }

    @DeleteMapping("/{classroomName}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteClassroom(@PathVariable String classroomName) {
        classroomService.deleteClassroom(classroomName);
    }

    @GetMapping("/{classroomName}")
    public ClassroomDetailInfoDTO getClassroomDetailInfo(
            @PathVariable String classroomName){
        Classroom classroom = classroomService
                .getClassroomDetailForRequester(classroomName);

        //TODO: use mapper instead
        ClassroomDetailInfoDTO classroomInfoDTO = new ClassroomDetailInfoDTO();
        classroomInfoDTO.setName(classroom.getName());
        classroomInfoDTO.setDescription(classroom.getDescription());
        classroomInfoDTO.setLesson(new LessonDto(classroom.getLesson().getName(),
                classroom.getLesson().getDescription()));
        User teacher = classroom.getTeacher();
        classroomInfoDTO.setTeacherInfo(new UserInfoDTO(teacher.getFirstName(),
                teacher.getLastName(), teacher.getEmail()));
        List<User> students = classroom.getStudents();
        List<UserInfoDTO> studentsInfo;
        if(students == null || students.isEmpty()){
            studentsInfo = null;
        }else{
            studentsInfo = new ArrayList<>();
            for(User s : students){
                studentsInfo.add(new UserInfoDTO
                        (s.getFirstName(), s.getLastName(), s.getEmail()));
            }
        }
        classroomInfoDTO.setStudentsInfo(studentsInfo);
        //

        return classroomInfoDTO;
    }

    @GetMapping("/{classroomName}/exercises")
    public List<ExerciseSubmission> getClassroomExercises(
            @PathVariable String classroomName){
        List<ExerciseSubmission> exercises = classroomService
                .getExercises(classroomName);

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
}