package ir.ac.kntu.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String subject;

    private String description;

    @ElementCollection(targetClass = String.class)
    private List<String> fileUrls;

    @ManyToOne
    private User creator;

    @Setter(AccessLevel.NONE)
    @LastModifiedDate
    private Timestamp createAt;

    private Timestamp deadline;

    private Timestamp lateDeadline;

    private ExerciseAccessLevel accessLevel;

    @ManyToOne
    private Classroom classroom;

    @ElementCollection(targetClass = String.class)
    private List<String> answersUrl;
}
