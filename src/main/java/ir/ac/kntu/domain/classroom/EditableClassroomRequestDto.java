package ir.ac.kntu.domain.classroom;

import ir.ac.kntu.domain.user.UserInfoDTO;
import ir.ac.kntu.model.Lesson;
import ir.ac.kntu.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Morteza Taghaddomi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditableClassroomRequestDto {
    private String name;
    private String description;
    private LessonDto lesson;
    private List<UserInfoDTO> assistant;
    private List<UserInfoDTO> students;
}
