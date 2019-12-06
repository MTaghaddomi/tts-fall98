package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.classroom.TeacherDto;
import ir.ac.kntu.model.User;
import org.mapstruct.Mapper;

/**
 * @author Morteza Taghaddomi
 */
@Mapper
public interface TeacherMapper {
    User convertToTeacherDto(TeacherDto teacherDto);

    TeacherDto convertToTeacherDto(User teacher);
}
