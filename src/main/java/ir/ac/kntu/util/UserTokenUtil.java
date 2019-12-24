package ir.ac.kntu.util;

import ir.ac.kntu.exception.UserNotExistedException;
import ir.ac.kntu.model.User;
import ir.ac.kntu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserTokenUtil {

    @Autowired
    private UserRepository userRepository;

    public String token2Username(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = null;
        }

        return username;
    }

    public User token2User(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            return userRepository.findUserByUsername(username)
                    .orElseThrow(UserNotExistedException::new);
        } else {
            String username = null;
            return null;
        }
    }
}
