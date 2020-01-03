package ir.ac.kntu.domain.classroom;

import ir.ac.kntu.domain.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    public List<UserInfoDTO> getAssistant() {
        if (assistant == null) {
            assistant = new ArrayList<>();
        }
        return assistant;
    }

    public List<UserInfoDTO> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }
}
