package ir.ac.kntu.domain.classroom;

import ir.ac.kntu.controller.UserRole;
import ir.ac.kntu.domain.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDetailInfoDTO {
    private String name;
    private String description;
    private LessonDto lesson;
    private UserInfoDTO teacherInfo;
    private UserRole role;
    private List<UserInfoDTO> studentsInfo;//for teacher exist//other null
}