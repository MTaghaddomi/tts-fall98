package ir.ac.kntu.controller;

import ir.ac.kntu.domain.ClassroomDto;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.service.ClassroomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<ClassroomDto> findAllClassrooms() {
        List<Classroom> classrooms = classroomService.findAll();
        return classrooms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    @GetMapping("/search")
    public List<ClassroomDto> searchClassroom(@RequestParam String searchItem) {
        List<Classroom> classrooms = classroomService.searchClassroom(searchItem);
        return classrooms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ClassroomDto createClassroom(@RequestParam ClassroomDto classroomDto) {
        Classroom classroom = convertToEntity(classroomDto);
        System.out.println(classroom);
        return convertToDto(classroomService.createClassroom(classroom));
    }

    @PutMapping
    public ClassroomDto updateClassroom(@RequestParam ClassroomDto classroomDto) {
        Classroom classroom = convertToEntity(classroomDto);
        return classroomDto;
    }

    @DeleteMapping
    public ClassroomDto deleteClassroom(@RequestParam ClassroomDto classroomDto) {
        return null;
    }

    private Classroom convertToEntity(ClassroomDto classroomDto) {
        Classroom classroom = Classroom.builder()
                .id(classroomDto.getId())
                .description(classroomDto.getDescription())
                .name(classroomDto.getName())
                .teacher(classroomDto.getTeacher())
                .lesson(classroomDto.getLesson())
                .build();
        return classroom;
    }

    private ClassroomDto convertToDto(Classroom classrooms) {
        ClassroomDto dto = ClassroomDto.builder()
                .id(classrooms.getId())
                .description(classrooms.getDescription())
                .lesson(classrooms.getLesson())
                .teacher(classrooms.getTeacher())
                .name(classrooms.getName())
                .build();
        return dto;

    }


}
