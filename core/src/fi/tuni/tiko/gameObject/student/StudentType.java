package fi.tuni.tiko.gameObject.student;

/**
 * Allows deserialization reference for all students in one place
 */
public enum StudentType {

    STUDENT1(Student1.getInstance()),
    STUDENT2(Student2.getInstance()),
    STUDENT3(Student3.getInstance());

    public final Student type;

    StudentType(Student instance) {
        type = instance;
    }
}
