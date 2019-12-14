package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.classroom.LessonDto;
import ir.ac.kntu.domain.classroom.RegisterClassroomRequestDto;
import ir.ac.kntu.domain.classroom.TeacherDto;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.Lesson;
import ir.ac.kntu.model.User;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;

/**
 * @author Morteza Taghaddomi
 */
public class ClassroomMapperTest {
    ClassroomMapper mapper = Mappers.getMapper(ClassroomMapper.class);

    @Test
    public void givenClassroomToDto_whenMaps_thenCorrect() {
        // Given
        User teacher = User.builder()
                .username("morteza_tm")
                .password("12345678")
                .firstName("morteza")
                .lastName("taghaddomi")
                .email("morteza@gmail.com")
                .build();

        Lesson advancedProgramming = Lesson.builder()
                .name("Advanced Programming")
                .build();

        Classroom ap981 = Classroom.builder()
                .name("ap981")
                .description("advanced programming fall 98")
                .lesson(advancedProgramming)
                .teacher(teacher)
                .build();

        // When
        RegisterClassroomRequestDto dto = mapper.convertClassroomRegisterDto(ap981);
        System.out.println(dto);

        // Then
        assertEquals(ap981.getName(), dto.getName());
        assertEquals(ap981.getDescription(), dto.getDescription());
        assertEquals(ap981.getName(), dto.getName());
        assertEquals(ap981.getLesson().getName(), dto.getLesson().getName());
    }

    @Test
    public void givenDtoToClassroom_whenMaps_thenCorrect() {
        // Given
        TeacherDto teacher = TeacherDto.builder()
                .firstName("morteza")
                .lastName("taghaddomi")
                .email("morteza@gmail.com")
                .build();

        LessonDto advanced_programming = LessonDto
                .builder()
                .name("Advanced Programming")
                .build();

        RegisterClassroomRequestDto ap981 = RegisterClassroomRequestDto.builder()
                .name("ap981")
                .description("advanced programming fall 98")
                .lesson(advanced_programming)
//                .teacher(teacher)
                .build();

        // When
        Classroom classroom = mapper.convertClassroomRegisterDto(ap981);
        System.out.println(classroom);

        // Then
        assertEquals(ap981.getName(), classroom.getName());
        assertEquals(ap981.getDescription(), classroom.getDescription());
        assertEquals(ap981.getName(), classroom.getName());
        assertEquals(ap981.getLesson().getName(), classroom.getLesson().getName());
    }


}

