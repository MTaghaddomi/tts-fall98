package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.classroom.LessonDto;
import ir.ac.kntu.model.Lesson;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

/**
 * @author Morteza Taghaddomi
 */

public class LessonMapperTest {
    LessonMapper mapper = Mappers.getMapper(LessonMapper.class);

    @Test
    public void givenLessonToDto_whenMaps_thenCorrect() {
        Lesson lesson = Lesson.builder()
                .name("Advanced Programming")
                .build();

        LessonDto lessonDto = mapper.convertLessonDto(lesson);
        System.out.println(lessonDto);

        Assert.assertEquals(lesson.getName(), lesson.getName());
    }

}
