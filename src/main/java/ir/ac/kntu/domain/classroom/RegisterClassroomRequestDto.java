package ir.ac.kntu.domain.classroom;

import ir.ac.kntu.model.Lesson;
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

//    private TeacherDto teacher;

    private Lesson lesson;
}
