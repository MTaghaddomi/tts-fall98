package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.classroom.LessonDto;
import ir.ac.kntu.model.Lesson;
import org.mapstruct.Mapper;

/**
 * @author Morteza Taghaddomi
 */
@Mapper
public interface LessonMapper {

    LessonDto convertLessonDto(Lesson lesson);

    Lesson convertLesson(LessonDto lessonDto);
}
