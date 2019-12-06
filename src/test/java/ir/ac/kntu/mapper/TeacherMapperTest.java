package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.classroom.TeacherDto;
import ir.ac.kntu.model.User;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;

/**
 * @author Morteza Taghaddomi
 */

public class TeacherMapperTest {
    TeacherMapper mapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    public void givenTeacherToDto_whenMaps_thenCorrect() {
        // Given
        User teacher = User.builder()
                .email("morteza@tm.com")
                .firstName("morteza")
                .lastName("taghaddomi")
                .build();

        // when
        TeacherDto teacherDto = mapper.convertToTeacherDto(teacher);
        System.out.println(teacherDto);

        // then
        assertEquals(teacher.getEmail(), teacherDto.getEmail());
        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
        assertEquals(teacher.getLastName(), teacherDto.getLastName());


    }
}
