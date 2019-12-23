package ir.ac.kntu.service;

import ir.ac.kntu.domain.user.UserSignUpRequestDTO;
import ir.ac.kntu.domain.user.UserSignUpResponseDTO;
import ir.ac.kntu.exception.DuplicateUserException;
import ir.ac.kntu.exception.UserNotExistedException;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.User;
import ir.ac.kntu.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private final static String MANAGER_USERNAME = "manager";
    private final static String TESTER_USERNAME = "tester";
    private final static String DEVELOPER_USERNAME = "developer";

    @Before
    public void setUp() {
        User manager = new User();
        manager.setFirstName("manager_first");
        manager.setLastName("manager_last");
        manager.setUsername(MANAGER_USERNAME);
        manager.setPassword("12345678");
        manager.setEmail("manager@a.b");

        Mockito.when(userRepository.findUserByUsername(manager.getUsername()))
                .thenReturn(Optional.of(manager));

        User tester = new User();
        tester.setFirstName("tester_first");
        tester.setLastName("tester_last");
        tester.setUsername(TESTER_USERNAME);
        tester.setPassword("11111111");
        tester.setEmail("tester@a.b");

        Mockito.when(userRepository.findUserByUsername(tester.getUsername()))
                .thenReturn(Optional.of(tester));

        User developer = new User();
        developer.setFirstName("developer_first");
        developer.setLastName("developer_last");
        developer.setUsername(DEVELOPER_USERNAME);
        developer.setPassword("22222222");
        developer.setEmail("developer@a.b");

        Mockito.when(userRepository.findUserByUsername(developer.getUsername()))
                .thenReturn(Optional.of(developer));
    }

    @Test
    public void whenValidUsername_thenUserShouldBeFound() {
        User found = userService.findByUsername(MANAGER_USERNAME)
                .orElseThrow(UserNotExistedException::new);

        assertThat(found.getUsername())
                .isEqualTo(MANAGER_USERNAME);
    }

    @Test(expected = UserNotExistedException.class)
    public void whenInvalidUsername_thenThrowException() {
        userService.findByUsername("asdfghjkl")
                .orElseThrow(UserNotExistedException::new);
    }

    /*
    @Test
    public void whenDeleteValidUser_thenUserShouldBeDelete(){
        //TODO: how? really how? :|
        User manager = userRepository.findUserByUsername(MANAGER_USERNAME)
                .orElseThrow(UserNotExistedException::new);

//        userService.deleteUserByUsername(manager.getUsername());

        Optional<User> deleted = userRepository.findUserByUsername(manager.getUsername());
        assertThat(deleted.isEmpty()).isTrue();
    }

     */

    /*
    @Test(expected = UserNotExistedException.class)
    public void whenDeleteInvalidUser_thenThrowException(){
        userService.deleteUserByUsername("asdfghjkl");
    }

     */

    /*
    @Test
    public void whenCreateNewUser_thenUserShouldPersist(){
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setEmail("newUser@c.d");
        newUser.setPassword("zxcvbnm");
        newUser.setFirstName("newUser_first");
        newUser.setLastName("newUser_last");

        //TODO: how?????? :|
        Mockito.when(userRepository.save(newUser))
                .thenReturn(newUser);
        Mockito.when(userRepository.findUserByUsername(newUser.getUsername()))
                .thenReturn(Optional.of(newUser));

        UserSignUpResponseDTO afterSignUp = userService.signUp
                (new UserSignUpRequestDTO
                        (newUser.getUsername(), newUser.getPassword()));

        assertThat(afterSignUp).isNotNull();
    }

     */

    @Test(expected = DuplicateUserException.class)
    public void whenSignUpExistUser_thenThrowException(){
        User againManager = userRepository.findUserByUsername(MANAGER_USERNAME)
                .orElseThrow(UserNotExistedException::new);

        userService.signUp(new UserSignUpRequestDTO
                        (againManager.getUsername(), againManager.getPassword()));
    }

    @Test
    public void whenValidUsernameGetClasses_thenShouldGetItsClasses(){
        User tester = userRepository.findUserByUsername(TESTER_USERNAME)
                .orElseThrow(UserNotExistedException::new);

        Classroom classUnitTest = new Classroom();
        classUnitTest.setName("unit_test");
        Classroom classIntegrationTest = new Classroom();
        classIntegrationTest.setName("integration_test");

        tester.addMeToClass(classUnitTest);
        tester.addMeToClass(classIntegrationTest);

        List<Classroom> classrooms = userService.getUserClasses(tester.getUsername());

        assertThat(classrooms).isNotNull();
        assertThat(classrooms.size()).isEqualTo(tester.getMyClasses().size());
    }

    @Test
    public void whenValidUsernameGetClasses_thenShouldGetItsClasses_whereNotJoinedAnyClass(){
        User tester = userRepository.findUserByUsername(TESTER_USERNAME)
                .orElseThrow(UserNotExistedException::new);

        List<Classroom> classrooms = userService.getUserClasses(tester.getUsername());

        assertThat(classrooms).isNull();
    }

    @Test(expected = UserNotExistedException.class)
    public void whenInvalidUsernameGetClasses_thenThrowException(){
        String tempUsername = "asdfgh";

        userService.getUserClasses(tempUsername);
    }
}
