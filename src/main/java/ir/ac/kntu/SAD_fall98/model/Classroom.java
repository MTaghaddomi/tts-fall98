package ir.ac.kntu.SAD_fall98.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    private String description;

    @OneToOne
    private User teacher;

    @OneToOne(cascade = CascadeType.ALL)
    private Lesson lesson;

    @OneToMany(cascade = CascadeType.ALL)
    private List<User> assistant;

    @OneToMany(cascade = CascadeType.ALL)
    private List<User> students;


}
