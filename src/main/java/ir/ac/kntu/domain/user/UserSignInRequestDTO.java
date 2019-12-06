package ir.ac.kntu.domain.user;

import ir.ac.kntu.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRequestDTO {
    private String username;
    private String password;

    public User convertToUser() {
        User user = User.builder().username(username).password(password).build();
        return user;
    }
}
