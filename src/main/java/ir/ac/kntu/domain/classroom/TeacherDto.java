package ir.ac.kntu.domain.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

/**
 * @author Morteza Taghaddomi
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {

    private String firstName;

    private String lastName;

    @Email
    private String email;

}
