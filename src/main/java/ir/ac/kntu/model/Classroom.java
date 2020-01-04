package ir.ac.kntu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.Wither;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    private String description;

    @OneToOne
    private User teacher;

    @OneToOne(cascade = CascadeType.ALL)
    private Lesson lesson;

    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ta",
            joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ta_id", referencedColumnName = "id"))
    private List<User> assistants;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "participate",
            joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private List<User> students;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classroom")
    private List<ExerciseSubmission> exercises;

    public void addExercise(ExerciseSubmission exerciseSubmission) {
        if (exercises == null) {
            this.exercises = new ArrayList<>();
        }

        this.exercises.add(exerciseSubmission);

        exerciseSubmission.setClassroom(this);
    }

    public void addAssistant(User assistant){
        if(this.assistants == null){
            this.assistants = new ArrayList<>();
        }

        this.assistants.add(assistant);
    }

    public void removeAssistant(User assistant){
        if(this.assistants != null){
            this.assistants.remove(assistant);
        }
    }

    public void removeAllAssistants(){
        this.assistants = new ArrayList<>();
    }

    public void addStudent(User student){
        if(this.students == null){
            this.students = new ArrayList<>();
        }

        student.addMeToClass(this);
        this.students.add(student);
    }

    public void removeStudent(User student){
        if(this.students != null){
            this.students.remove(student);
            student.removeMeFromClass(this);
        }
    }

    public void removeAllStudents(){
        for(User student : students){
            student.removeMeFromClass(this);
        }

        this.students = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Classroom)) return false;
        Classroom classroom = (Classroom) o;
        return id == classroom.id &&
                Objects.equal(name, classroom.name) &&
                Objects.equal(teacher, classroom.teacher) &&
                Objects.equal(lesson, classroom.lesson);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, teacher, lesson);
    }

    public List<User> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }

    public List<User> getAssistants() {
        if (assistants == null) {
            assistants = new ArrayList<>();
        }
        return assistants;
    }


}
