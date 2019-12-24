package ir.ac.kntu.repository;

import ir.ac.kntu.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void persistUsers(){
        User manager = new User();
        manager.setFirstName("manager_first");
        manager.setLastName("manager_last");
        manager.setUsername("manager");
        manager.setPassword("12345678");
        manager.setEmail("manager@a.b");
        entityManager.persist(manager);
    }

    @Test
    public void whenFindByUsername_thenReturnUser_manager() {
        // given
        String managerUsername = "manager";

        // when
        User found = userRepository.findUserByUsername(managerUsername).get();

        // then
        assertThat(found.getUsername()).isEqualTo(managerUsername);
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        User endUser = new User();
        endUser.setUsername("test_user");
        endUser.setPassword("12345678");
        entityManager.persist(endUser);
        entityManager.flush();

        // when
        User found = userRepository.findUserByUsername(endUser.getUsername()).get();

        // then
        assertThat(found.getUsername()).isEqualTo(endUser.getUsername());
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User endUser = new User();
        endUser.setUsername("test_user");
        endUser.setPassword("12345678");
        endUser.setEmail("test@c.d");
        entityManager.persist(endUser);
        entityManager.flush();

        // when
        User found = userRepository.findUserByEmail(endUser.getEmail()).get();

        // then
        assertThat(found.getEmail()).isEqualTo(endUser.getEmail());
    }
}
