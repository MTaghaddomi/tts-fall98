package ir.ac.kntu.model;

import com.google.common.base.Objects;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    @Setter(AccessLevel.NONE)
    @ManyToMany(//fetch = FetchType.LAZY,
            //cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "students")
    private List<Classroom> myClasses;

    public void addMeToClass(Classroom classroom){
        if(classroom == null){
            throw new NullPointerException();
        }

        if(myClasses == null){
            myClasses = new ArrayList<>();
        }

        myClasses.add(classroom);
    }

    public void removeMeFromClass(Classroom classroom){
        if(classroom == null){
            throw new NullPointerException();
        }

        if(myClasses != null){
            myClasses.remove(classroom);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equal(firstName, user.firstName) &&
                Objects.equal(lastName, user.lastName) &&
                Objects.equal(username, user.username) &&
                Objects.equal(email, user.email) &&
                Objects.equal(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName, lastName, username, email, phoneNumber);
    }
}
