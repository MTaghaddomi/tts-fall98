package ir.ac.kntu.SAD_fall98.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //        @NotBlank(message = "firstname must not be blank")
//    @Length(min = 5, max = 32)
    private String firstName;

    private String lastName;

    @NotBlank
    @Length(min = 5, max = 32)
    private String username;

    @NotBlank
//    @ValidPassword
    @Length(min = 6, max = 30, message = "password length must be between 0 and 30")
    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDate birthday;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
