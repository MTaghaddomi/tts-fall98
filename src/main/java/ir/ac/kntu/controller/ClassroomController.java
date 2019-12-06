package ir.ac.kntu.controller;

import ir.ac.kntu.domain.classroom.*;
import ir.ac.kntu.mapper.ClassroomMapper;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.service.ClassroomService;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classrooms")
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

    @GetMapping("/{lessonName}")
    public List<SearchClassroomDto> findAllClassroomsByLesson(@PathVariable String lessonName) {
        List<Classroom> classrooms = classroomService.findByLessonName(lessonName);
        return classrooms.stream()
                .map(mapper::convertSearchClassroomDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/search")
    public List<RegisterClassroomRequestDto> searchClassroom(@RequestParam String searchItem) {
        List<Classroom> classrooms = classroomService.searchClassroom(searchItem);

        return classrooms.stream()
                .map(mapper::convertClassroomRegisterDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public RegisterClassroomResponseDto createClassroom(@RequestParam RegisterClassroomRequestDto registerClassDto) {
        Classroom classroom = mapper.convertClassroomRegisterDto(registerClassDto);
        classroomService.createClassroom(classroom);
        return new RegisterClassroomResponseDto();
    }

    @PutMapping
    public EditableClassroomResponseDto updateClassroom(@RequestParam EditableClassroomRequestDto editClassDto) {
        Classroom classroom = mapper.convertEditableClassroomDto(editClassDto);
        classroomService.updateClassroom(classroom);
        return new EditableClassroomResponseDto();
    }

    @DeleteMapping("/{classroomName}")
    public void deleteClassroom(@PathVariable String classroomName) {
        classroomService.deleteClassroom(classroomName);
    }

//    private Classroom convertToEntity(RegisterClassroomRequestDto registerClassroomRequestDto) {
//        Classroom classroom = Classroom.builder()
//                .id(registerClassroomRequestDto.getId())
//                .description(registerClassroomRequestDto.getDescription())
//                .name(registerClassroomRequestDto.getName())
//                .teacher(registerClassroomRequestDto.getTeacher())
//                .lesson(registerClassroomRequestDto.getLesson())
//                .build();
//        return classroom;
//    }
//
//    private RegisterClassroomRequestDto convertToDto(Classroom classrooms) {
//        RegisterClassroomRequestDto dto = RegisterClassroomRequestDto.builder()
//                .id(classrooms.getId())
//                .description(classrooms.getDescription())
//                .lesson(classrooms.getLesson())
//                .teacher(classrooms.getTeacher())
//                .name(classrooms.getName())
//                .build();
//        return dto;
//
//    }


}
