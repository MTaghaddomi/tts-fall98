package ir.ac.kntu.domain.submission;

import ir.ac.kntu.model.ExerciseAccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSubmissionRequestDTO {
    @NotBlank
    private String subject;

    private String description;

    private Timestamp deadline;

    private Timestamp lateDeadline;

    private ExerciseAccessLevel accessLevel;
}
