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

    private UserInfoDTO creator;

    private List<String> fileUrls;

    public AnswerSubmissionInfoDTO(long id, String text, List<String> fileUrls) {
        this.id = id;
        this.text = text;
        this.fileUrls = fileUrls;
    }
}
