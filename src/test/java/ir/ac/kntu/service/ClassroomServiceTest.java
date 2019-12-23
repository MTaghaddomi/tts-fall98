package ir.ac.kntu.service;

import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.model.Lesson;
import ir.ac.kntu.model.User;
import ir.ac.kntu.repository.ClassroomRepository;
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
public class ClassroomServiceTest {
    @Autowired
    private ClassroomService classroomService;

    @MockBean
    private ClassroomRepository classroomRepository;

    @MockBean
    private UserRepository userRepository;

    private final static String JAVA_CONTAIN = "java";
    private final static String TEST_CONTAIN = "test";
    private final static String CLASS_CONTAIN = "class";

    @Before
    public void setUp(){
        User teacher = User.builder().id(987654321)
                .firstName("teacher_first").lastName("teacher_last")
                .username("teacher").password("12345678")
                .email("teacher@a.b").build();

        Lesson javaLesson = Lesson.builder().id(1234).name("java").build();
        Lesson testLesson = Lesson.builder().id(5678).name("test").build();

        Classroom javaClass1 = Classroom.builder().id(1000)
                .name("java-class1")
                .description("intro java1")
                .teacher(teacher)
                .lesson(javaLesson).build();

        Classroom javaClass2 = Classroom.builder().id(1001)
                .name("java-class2")
                .description("intro java2")
                .teacher(teacher)
                .lesson(javaLesson).build();

        Mockito.when(classroomRepository.findClassroomByName(javaClass1.getName()))
                .thenReturn(Optional.of(javaClass1));
        Mockito.when(classroomRepository.findClassroomByName(javaClass2.getName()))
                .thenReturn(Optional.of(javaClass2));
        Mockito.when(classroomRepository.findClassroomsByLessonName(javaLesson.getName()))
                .thenReturn(List.of(javaClass1, javaClass2));
        Mockito.when(classroomRepository.findAllClassroomsByNameContains(JAVA_CONTAIN))
                .thenReturn(List.of(javaClass1, javaClass2));

        Classroom testClass = Classroom.builder().id(2000)
                .name("test-class")
                .description("intro test")
                .teacher(teacher)
                .lesson(testLesson).build();

        Mockito.when(classroomRepository.findClassroomByName(testClass.getName()))
                .thenReturn(Optional.of(testClass));

        Mockito.when(classroomRepository.findClassroomsByLessonName(testLesson.getName()))
                .thenReturn(List.of(testClass));

        Mockito.when(classroomRepository.findAllClassroomsByNameContains(TEST_CONTAIN))
                .thenReturn(List.of(testClass));


        Mockito.when(classroomRepository.findAllClassroomsByNameContains(CLASS_CONTAIN))
                .thenReturn(List.of(javaClass1, javaClass2, testClass));

        Mockito.when(classroomRepository.findAll())
                .thenReturn(List.of(javaClass1, javaClass2, testClass));
    }

    @Test
    public void whenSearchContainNameJava_thenReturnJavaClasses(){
        String className = JAVA_CONTAIN;
        List<Classroom> classroomsContainName =
                classroomService.searchClassroom(className, null);
        assertThat(classroomsContainName)
                .isSameAs(classroomRepository.findAllClassroomsByNameContains(className));

        String lessonName = JAVA_CONTAIN;
        List<Classroom> classroomsByLesson =
                classroomService.searchClassroom(null, lessonName);
        assertThat(classroomsByLesson)
                .isSameAs(classroomRepository.findClassroomsByLessonName(lessonName));
    }

    @Test
    public void whenTeacherGetClassroomDetail_thenReturnAllDetail(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User teacher = classroom.getTeacher();

        assertThat(classroomService.getClassroomDetailForRequester
                (teacher.getUsername(), classroom.getName()))
                .isSameAs(classroom);
    }

    @Test
    public void whenAssistantGetClassroomDetail_thenReturnAllDetail(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User assistant = User.builder().id(9999).username("theAssistant").password("11111111").build();
        classroom.addAssistant(assistant);

        assertThat(classroomService.getClassroomDetailForRequester
                (assistant.getUsername(), classroom.getName()))
                .isSameAs(classroom);
    }

    @Test
    public void whenStudentGetClassroomDetail_thenReturnDetailWithoutStudents(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User student = User.builder().id(9999).username("theStudent").password("11111111").build();
        classroom.addStudent(student);

        assertThat(classroomService.getClassroomDetailForRequester
                (student.getUsername(), classroom.getName()))
                .isEqualTo(classroom.withStudents(null));
    }

    @Test
    public void whenOtherGetClassroomDetail_thenReturnDetailWithoutStudentsAndAssistants(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User student = User.builder().id(9999).username("theStudent").password("11111111").build();
        classroom.addStudent(student);
        User assistant = User.builder().id(8888).username("theAssistant").password("11111111").build();
        classroom.addAssistant(assistant);

        assertThat(classroomService.getClassroomDetailForRequester
                ("no_one", classroom.getName()))
                .isEqualTo(classroom.withStudents(null).withAssistants(null));
    }

    @Test
    public void whenIsTeacher_thenReturnIsTeacherNotOtherRoles(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User teacher = classroom.getTeacher();

        assertThat(classroomService.isTeacherIn(teacher.getUsername(), classroom))
                .isTrue();
        assertThat(classroomService.isAssistantIn(teacher.getUsername(), classroom))
                .isFalse();
        assertThat(classroomService.isStudentIn(teacher.getUsername(), classroom))
                .isFalse();
    }

    @Test
    public void whenIsAssistant_thenReturnIsAssistantNotOtherRoles(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User assistant = User.builder().id(9999).username("theAssistant").password("11111111").build();
        classroom.addAssistant(assistant);

        assertThat(classroomService.isTeacherIn(assistant.getUsername(), classroom))
                .isFalse();
        assertThat(classroomService.isAssistantIn(assistant.getUsername(), classroom))
                .isTrue();
        assertThat(classroomService.isStudentIn(assistant.getUsername(), classroom))
                .isFalse();
    }

    @Test
    public void whenIsStudent_thenReturnIsStudentNotOtherRoles(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User student = User.builder().id(9999).username("theStudent").password("11111111").build();
        classroom.addStudent(student);
        User otherStudent = User.builder().id(8888).username("theOtherStudent").password("11111111").build();
        classroom.addStudent(otherStudent);

        assertThat(classroomService.isTeacherIn(student.getUsername(), classroom))
                .isFalse();
        assertThat(classroomService.isAssistantIn(student.getUsername(), classroom))
                .isFalse();
        assertThat(classroomService.isStudentIn(student.getUsername(), classroom))
                .isTrue();

        assertThat(classroomService.isTeacherIn(otherStudent.getUsername(), classroom))
                .isFalse();
        assertThat(classroomService.isAssistantIn(otherStudent.getUsername(), classroom))
                .isFalse();
        assertThat(classroomService.isStudentIn(otherStudent.getUsername(), classroom))
                .isTrue();
    }

    @Test
    public void whenIsNotRelatedUser_thenReturnNoRole(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User tempUser = User.builder().id(9999).username("notRelated").password("11111111").build();

        assertThat(classroomService.isTeacherIn(tempUser.getUsername(), classroom))
                .isFalse();
        assertThat(classroomService.isAssistantIn(tempUser.getUsername(), classroom))
                .isFalse();
        assertThat(classroomService.isStudentIn(tempUser.getUsername(), classroom))
                .isFalse();
    }

    @Test
    public void whenJoinClassroom_thenUpdateStudentsList(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User student = User.builder().id(9999).username("theStudent").password("11111111").build();
        Mockito.when(userRepository.findUserByUsername(student.getUsername()))
                .thenReturn(Optional.of(student));

        assertThat(classroomService.joinClass(student.getUsername(), classroom.getName()))
                .isEqualTo(classroom.withStudents(List.of(student)));
    }

    @Test
    public void whenJoinClassroom_thenUpdateUserClassesList(){
        Classroom classroom = classroomRepository.findAll().get(0);
        User student = User.builder().id(9999).username("theStudent").password("11111111").build();
        Mockito.when(userRepository.findUserByUsername(student.getUsername()))
                .thenReturn(Optional.of(student));

        List<User> students = classroomService.joinClass(student.getUsername(), classroom.getName()).getStudents();
        User theStudent = students.get(0);
        assertThat(theStudent.getMyClasses()).isNotNull();
        assertThat(theStudent.getMyClasses().size()).isEqualTo(1);
        assertThat(theStudent.getMyClasses().get(0).getName())
                .isEqualTo(classroom.getName());
    }

    //TODO: how to test "create" and "delete" and "update"?

    //TODO test get exercise
}
