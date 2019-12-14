package ir.ac.kntu.domain.submission;

import ir.ac.kntu.domain.user.UserInfoDTO;
import ir.ac.kntu.model.ExerciseAccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSubmissionDetailInfoDTO {

    private long id;

    @NotBlank
    private String subject;

    private String description;

    private Timestamp deadline;

    private Timestamp lateDeadline;

    private ExerciseAccessLevel accessLevel;

    private List<String> fileUrls;
}
