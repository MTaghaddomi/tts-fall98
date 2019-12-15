package ir.ac.kntu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    @NotBlank
    @Length(min = 4, max = 20)
    private String username;

    //    @Length(min = 6, max = 30, message = "password length must be between 6 and 30")
    @NotBlank
    private String password;

//    @Email
    private String email;

    private Timestamp createdAt;

    private Timestamp birthday;

//    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
//    @ColumnDefault(value = "1")
//    private boolean subscribed;

//    @Length(min = 11, max = 11, message = "phone number should has 11 digit")
//    @Pattern(regexp = "09\\d{9}")
    private String phoneNumber;

    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
    private List<Classroom> myClasses;
}
