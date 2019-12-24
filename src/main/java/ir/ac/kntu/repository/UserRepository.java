package ir.ac.kntu.repository;

import ir.ac.kntu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> deleteUserByUsername(String username);
    Optional<User> deleteUserByEmail(String email);

}
