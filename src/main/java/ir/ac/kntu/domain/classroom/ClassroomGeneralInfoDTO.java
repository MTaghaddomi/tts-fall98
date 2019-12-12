package ir.ac.kntu.domain.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomGeneralInfoDTO {
    private String name;
    private String lesson;
    private String teacher;
}
