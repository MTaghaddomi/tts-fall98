package ir.ac.kntu.SAD_fall98.repository;

import ir.ac.kntu.SAD_fall98.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
