package ir.ac.kntu.domain.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Morteza Taghaddomi
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchClassroomDto {
    private String name;

    private String description;

    private String teacherName;

    private String lessonName;
}
