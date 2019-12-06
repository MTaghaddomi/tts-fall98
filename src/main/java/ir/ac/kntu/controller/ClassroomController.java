package ir.ac.kntu.controller;

import ir.ac.kntu.domain.classroom.EditableClassroomRequestDto;
import ir.ac.kntu.domain.classroom.RegisterClassroomRequestDto;
import ir.ac.kntu.domain.classroom.RegisterClassroomResponseDto;
import ir.ac.kntu.domain.classroom.SearchClassroomDto;
import ir.ac.kntu.mapper.ClassroomMapper;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.service.ClassroomService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}