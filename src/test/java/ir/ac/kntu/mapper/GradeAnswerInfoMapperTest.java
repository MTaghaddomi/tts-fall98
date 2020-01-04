package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.submission.GradeAnswerInfoRequestDTO;
import ir.ac.kntu.domain.submission.GradeAnswerInfoResponseDTO;
import ir.ac.kntu.model.AnswerSubmission;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Morteza Taghaddomi
 */
public class GradeAnswerInfoMapperTest {
    private GradeAnswerInfoMapper mapper = Mappers.getMapper(GradeAnswerInfoMapper.class);

    @Test
    public void convertToGradeAnswerRequestDto() {
        var dto = GradeAnswerInfoRequestDTO.builder()
                .grade(10)
                .text("test text")
                .fileUrls(List.of("resources/hw/", "resources/kntu"))
                .build();


        AnswerSubmission answerSubmission = mapper.convertToGradeAnswerRequestDto(dto);

        assertEquals(answerSubmission.getText(), dto.getText());
        assertEquals(answerSubmission.getGrade(), dto.getGrade(), 0.01);

    }


    @Test
    public void convertToGradeAnswerResponseDto() {
        var dto = GradeAnswerInfoResponseDTO.builder()
                .grade(10)
                .text("text")
                .build();

        AnswerSubmission answerSubmission = mapper.convertToGradeAnswerResponseDto(dto);

        assertEquals(answerSubmission.getText(), dto.getText());
        assertEquals(answerSubmission.getGrade(), dto.getGrade(), 0.01);
    }

    @Test
    public void convertDtoToGradeAnswerResponse() {

        var answerSubmission = AnswerSubmission.builder()
                .text("test text")
                .grade(9.5)
                .build();

        var dto = mapper.convertToGradeAnswerResponseDto(answerSubmission);

        assertEquals(answerSubmission.getText(), dto.getText());
        assertEquals(answerSubmission.getGrade(), dto.getGrade(), 0.01);
    }
}