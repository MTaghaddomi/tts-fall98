package ir.ac.kntu.service;

import ir.ac.kntu.domain.user.*;
import ir.ac.kntu.exception.DuplicateUserException;
import ir.ac.kntu.exception.UserNotExistedException;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.User;
import ir.ac.kntu.repository.ClassroomRepository;
import ir.ac.kntu.repository.UserRepository;
import ir.ac.kntu.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bcryptEncode;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ClassroomRepository classroomRepository;

    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO userDTO)
            throws DuplicateUserException {
        userDTO.setPassword(bcryptEncode.encode(userDTO.getPassword()));

        User savedUser = create(userDTO.convertToUser());

        UserSignUpResponseDTO response = UserSignUpResponseDTO.builder()
                .username(savedUser.getUsername())
                .token(generateToken(savedUser))
                .build();

        return response;
    }

    public UserSignInResponseDTO signIn(UserSignInRequestDTO userDTO)
            throws UserNotExistedException {
        userDTO.setPassword(bcryptEncode.encode(userDTO.getPassword()));

        if (!isUsernameExisted(userDTO.getUsername())) {
            throw new UserNotExistedException();
        }

        User user = userDTO.convertToUser();
        UserSignInResponseDTO response = UserSignInResponseDTO.builder()
                .username(user.getUsername())
                .token(generateToken(user))
                .build();

        return response;
    }

    private User create(User user) throws DuplicateUserException {
        if (isUsernameExisted(user.getUsername())) {
            throw new DuplicateUserException();
        }

        return userRepository.save(user);
    }

    private boolean isUsernameExisted(String username) {
        return findByUsername(username).isPresent();
    }

    private String generateToken(User user) {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(), new ArrayList<>());

        return jwtUtil.generateToken(userDetails);
    }

    public UserProfileDTO profile(String username) {
        User user = findByUsername(username).orElseThrow(UserNotExistedException::new);

        return UserProfileDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public UserProfileDTO editProfile(String username, UserEditableProfileDTO newProfile)
            throws UserNotExistedException {

        //TODO: restrict users to edit others profile!!!

        User user = findByUsername(username).orElseThrow(UserNotExistedException::new);

        user.setFirstName(newProfile.getFirstName());
        user.setLastName(newProfile.getLastName());
        user.setEmail(newProfile.getEmail());
        user.setBirthday(newProfile.getBirthday());
        user.setPhoneNumber(newProfile.getPhoneNumber());

        User savedUser = userRepository.save(user);

        return UserProfileDTO.builder()
                .username(username)
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .birthday(savedUser.getBirthday())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }

    public HttpStatus deleteUser(String username) {

        //TODO: restrict users to delete others account!!!

        User user = findByUsername(username).orElseThrow(UserNotExistedException::new);

        userRepository.delete(user);

        //deleteByUsername
        //userRepository.deleteUserByUsername(username);
        //TODO: need check if user not exist?

        return HttpStatus.OK;
    }

    public List<Classroom> getUserClasses(String username) {
        User user = findByUsername(username)
                .orElseThrow(UserNotExistedException::new);

        List<Classroom> myClasses = new ArrayList<>();
        myClasses.addAll(user.getMyClasses());

        List<Classroom> findAllClasses = classroomRepository.findAll();
        if (findAllClasses != null) {
            for (Classroom t : findAllClasses) {
                if (t.getTeacher().getUsername().equals(username)) {
                    myClasses.add(t);
                }
            }
        }


        return myClasses;
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findByUsername(s).orElseThrow(UserNotExistedException::new);

        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
