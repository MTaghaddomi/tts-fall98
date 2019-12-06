package ir.ac.kntu.domain.classroom;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Morteza Taghaddomi
 */
public class TeacherDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

}
