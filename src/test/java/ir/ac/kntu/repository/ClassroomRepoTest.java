package ir.ac.kntu.repository;

import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.Lesson;
import ir.ac.kntu.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Morteza Taghaddomi
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClassroomRepoTest {
    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void whenFindClassroomsByLessonName_thenReturnClassrooms() {
        // given
        User teacher = User.builder()
                .username("morteza_tm")
                .password("12345678")
                .firstName("morteza")
                .lastName("taghaddomi")
                .email("morteza@gmail.com")
                .build();

        Lesson advancedProgramming = Lesson.builder()
                .name("Advanced Programming")
                .build();

        Classroom ap981 = Classroom.builder()
                .name("ap981")
                .description("advanced programming fall 98")
                .lesson(advancedProgramming)
                .teacher(teacher)
                .build();

        userRepository.save(teacher);
        classroomRepository.save(ap981);

        // when
        List<Classroom> advanced_programming = classroomRepository.findClassroomsByLessonName("Advanced Programming");

        // then
        assertEquals(1, advanced_programming.size());
        assertEquals(advanced_programming.get(0), ap981);
    }

    @Test
    public void whenFindAllClassroomsByNameContains_thenReturnClassrooms() {
        // given
        User teacher = User.builder()
                .username("morteza_tm")
                .password("12345678")
                .firstName("morteza")
                .lastName("taghaddomi")
                .email("morteza@gmail.com")
                .build();

        Lesson advancedProgramming = Lesson.builder()
                .name("Advanced Programming")
                .build();

        Classroom ap981 = Classroom.builder()
                .name("ap981")
                .description("advanced programming fall 98")
                .lesson(advancedProgramming)
                .teacher(teacher)
                .build();

        userRepository.save(teacher);
        classroomRepository.save(ap981);

        // when
        List<Classroom> advanced_programming = classroomRepository.findAllClassroomsByNameContains("ap");

        // then
        assertEquals(1, advanced_programming.size());
        assertEquals(advanced_programming.get(0), ap981);
    }

    @Test
    public void whenFindByClassName_thenReturnClassroom() {
        //given
        User teacher = User.builder()
                .username("morteza_tm")
                .password("12345678")
                .firstName("morteza")
                .lastName("taghaddomi")
                .email("morteza@gmail.com")
                .build();

        Lesson advancedProgramming = Lesson.builder()
                .name("Advanced Programming")
                .build();

        Classroom ap981 = Classroom.builder()
                .name("ap981")
                .description("advanced programming fall 98")
                .lesson(advancedProgramming)
                .teacher(teacher)
                .build();

        userRepository.save(teacher);
        classroomRepository.save(ap981);

        // when
        Optional<Classroom> optionalClassroom = classroomRepository.findClassroomByName("ap981");

        // then
        assertTrue(optionalClassroom.isPresent());
        assertEquals(ap981, optionalClassroom.get());
    }


}
