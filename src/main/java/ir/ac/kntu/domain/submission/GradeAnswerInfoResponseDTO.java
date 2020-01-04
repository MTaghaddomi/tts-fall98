package ir.ac.kntu.domain.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeAnswerInfoResponseDTO {
    private long id;

    private String text;

    private double grade;
}
