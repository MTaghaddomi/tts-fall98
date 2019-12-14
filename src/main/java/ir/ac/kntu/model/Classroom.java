package ir.ac.kntu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    private String description;

    @OneToOne
    private User teacher;

    @OneToOne(cascade = CascadeType.ALL)
    private Lesson lesson;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ta",
            joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ta_id", referencedColumnName = "id"))
    private List<User> assistant;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "participate",
            joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private List<User> students;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ExerciseSubmission> exercises;

}
