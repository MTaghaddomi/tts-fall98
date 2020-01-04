package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.submission.AnswerSubmissionInfoDTO;
import ir.ac.kntu.model.AnswerSubmission;
import org.mapstruct.Mapper;

/**
 * @author Morteza Taghaddomi
 */
@Mapper
public interface AnswerSubmissionMapper {

    AnswerSubmissionInfoDTO convertAnswerSubmissionInfo(AnswerSubmission submission);

    AnswerSubmission convertAnswerSubmissionInfo(AnswerSubmissionInfoDTO submission);
}
