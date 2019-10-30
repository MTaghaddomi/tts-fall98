package ir.ac.kntu.SAD_fall98.model;


import org.springframework.data.annotation.CreatedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String username;

    private String password;

    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDate birthday;

    private String email;
    private LocalDateTime lastTimeLogin;
}
