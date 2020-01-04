package ir.ac.kntu.domain.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeAnswerInfoRequestDTO {
    private long id;

    private String text;

    private double grade;

    private List<String> fileUrls;
}
