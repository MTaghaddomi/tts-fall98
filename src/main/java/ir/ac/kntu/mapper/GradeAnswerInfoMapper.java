package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.submission.GradeAnswerInfoRequestDTO;
import ir.ac.kntu.domain.submission.GradeAnswerInfoResponseDTO;
import ir.ac.kntu.model.AnswerSubmission;
import org.mapstruct.Mapper;

/**
 * @author Morteza Taghaddomi
 */

@Mapper
public interface GradeAnswerInfoMapper {
    GradeAnswerInfoRequestDTO convertToGradeAnswerRequestDto(AnswerSubmission submission);

    AnswerSubmission convertToGradeAnswerRequestDto(GradeAnswerInfoRequestDTO answerSubmission);

    GradeAnswerInfoResponseDTO convertToGradeAnswerResponseDto(AnswerSubmission submission);

    AnswerSubmission convertToGradeAnswerResponseDto(GradeAnswerInfoResponseDTO answerSubmission);
}
