package ir.ac.kntu.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Timestamp birthday;
    private String phoneNumber;
}
