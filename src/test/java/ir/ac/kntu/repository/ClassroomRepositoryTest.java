package ir.ac.kntu.repository;

import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.Lesson;
import ir.ac.kntu.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClassroomRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User teacher = new User();
        teacher.setEmail("test@c.d");
        teacher.setUsername("test_user");
        teacher.setPassword("12345678");
        entityManager.persist(teacher);
        entityManager.flush();

        Lesson lesson = new Lesson();
        lesson.setName("temp_lesson");

        Classroom classroom = new Classroom();
        classroom.setName("temp_class");
        classroom.setTeacher(teacher);
        classroom.setLesson(lesson);
        entityManager.persist(classroom);
        entityManager.flush();

        // when
        Optional<Classroom> found = classroomRepository.findClassroomByName(classroom.getName());

        // then
        assertThat(found.isPresent()).isEqualTo(true);
    }

    @Test
    public void whenFindAllInEmptyRepo_thenReturnEmptyNull(){
        List<Classroom> allClasses = classroomRepository.findAll();
        assertThat(allClasses).isNotNull();
        assertThat(allClasses.size()).isEqualTo(0);
    }
}
