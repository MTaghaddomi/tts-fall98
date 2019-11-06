package ir.ac.kntu.domain;

import ir.ac.kntu.model.Lesson;
import ir.ac.kntu.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDto {
    private long id;

    private String name;

    private String description;

    private User teacher;

    private Lesson lesson;

}
