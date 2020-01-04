package ir.ac.kntu.domain.submission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSubmissionInfoDTO {
    private long id;

    private String text;

    private List<String> fileUrls;
}
