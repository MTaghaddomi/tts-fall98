package ir.ac.kntu.domain.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClassroomRequestDto {
    private String name;

    private String description;

    private LessonDto lesson;
}
