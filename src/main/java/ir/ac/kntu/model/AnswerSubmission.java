package ir.ac.kntu.model;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String text;

    private double grade;

    @ElementCollection(targetClass = String.class)
    private List<String> fileUrls;

    @Setter(AccessLevel.NONE)
    @LastModifiedDate
    private Timestamp createAt;

    @ManyToOne
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExerciseSubmission question;
}
