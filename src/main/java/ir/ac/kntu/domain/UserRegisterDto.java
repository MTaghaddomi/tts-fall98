package ir.ac.kntu.domain;


import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserRegisterDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Length(min = 5, max = 30, message = "username length must be between 5 and 30")
    private String username;

    @Length
    @NotBlank
    @Length(min = 6, max = 30, message = "password length must be between 6 and 30")
    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    private String lastName;

    @Length(min = 11, max = 11, message = "phone number should has 11 digits")
    @Pattern(regexp = "09\\d{9}")
    private String phoneNumber;

    private String birthday;

    @Email
    private String email;

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
