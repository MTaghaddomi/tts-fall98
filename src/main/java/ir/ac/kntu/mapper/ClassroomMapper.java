package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.classroom.EditableClassroomRequestDto;
import ir.ac.kntu.domain.classroom.RegisterClassroomRequestDto;
import ir.ac.kntu.domain.classroom.SearchClassroomDto;
import ir.ac.kntu.model.Classroom;
import org.mapstruct.Mapper;

/**
 * @author Morteza Taghaddomi
 */
@Mapper
public interface ClassroomMapper {
    RegisterClassroomRequestDto convertClassroomRegisterDto(Classroom classroom);

    Classroom convertClassroomRegisterDto(RegisterClassroomRequestDto registerDto);

    SearchClassroomDto convertSearchClassroomDto(Classroom classroom);

    Classroom convertSearchClassroomDto(SearchClassroomDto searchDto);

    Classroom convertEditableClassroomDto(EditableClassroomRequestDto editClassDto);

}
