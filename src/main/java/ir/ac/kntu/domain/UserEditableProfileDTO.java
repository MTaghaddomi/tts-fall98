package ir.ac.kntu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditableProfileDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Timestamp birthday;
    private String phoneNumber;
}
