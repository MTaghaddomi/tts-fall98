package ir.ac.kntu.domain.submission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSubmissionGeneralInfoDTO {
    private long id;

    private String subject;
}
