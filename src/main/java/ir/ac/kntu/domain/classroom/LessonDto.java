package ir.ac.kntu.domain.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Morteza Taghaddomi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private String name;
//    private String description;
}
