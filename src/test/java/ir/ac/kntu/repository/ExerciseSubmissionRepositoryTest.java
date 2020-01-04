package ir.ac.kntu.repository;

import ir.ac.kntu.model.ExerciseAccessLevel;
import ir.ac.kntu.model.ExerciseSubmission;
import ir.ac.kntu.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Morteza Taghaddomi
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExerciseSubmissionRepositoryTest {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ClassroomRepository classroomRepository;

    @Before
    public void setUp() {
        User user = User.builder()
                .username("morteza-tm")
                .password("12345678")
                .firstName("Morteza")
                .lastName("taghaddomi")
                .build();

        User teacher = User.builder()
                .username("my-teacher").build();

        userRepository.save(teacher);
        userRepository.flush();

        userRepository.findAll();
        Mockito.when(userRepository.findUserByUsername("morteza-tm"))
                .thenReturn(Optional.of(user));

        ExerciseSubmission exerciseSubmission = new ExerciseSubmission();
        exerciseSubmission.setAccessLevel(ExerciseAccessLevel.ALL_STUDENTS);
        exerciseSubmission.setDeadline(new Timestamp(1607774400));
//        exerciseSubmission.setCreator(teacher);
        exerciseSubmission.setSubject("recursion");

        exerciseRepository.save(exerciseSubmission);
//        exerciseRepository.flush();
    }

    @Test
    public void findSubmissionTest() {
        List<ExerciseSubmission> submissions = exerciseRepository.findAll();

        ExerciseSubmission submission = submissions.get(0);

        assertNotNull(submission);
        assertEquals(submission.getAccessLevel(), ExerciseAccessLevel.ALL_STUDENTS);
        assertEquals(submission.getSubject(), "recursion");
        assertEquals(submissions.size(), 1);
    }
}

