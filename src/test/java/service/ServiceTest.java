package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    public static Service service;

    @BeforeAll
    public static void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testFindAllStudents() {
        long numberOfStudentsBefore = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        Student student1 = new Student("26","Jancsi",555);
        Student student2 = new Student("27", "Juliska",555);
        service.saveStudent(student1.getID(),student1.getName(),student1.getGroup());
        service.saveStudent(student2.getID(),student2.getName(),student2.getGroup());
        long numberOfStudentsAfterSave = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.deleteStudent(student1.getID());
        service.deleteStudent(student2.getID());
        long numberOfStudentsAfterDelete = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();

        assertAll(
                () -> assertEquals(numberOfStudentsBefore+2, numberOfStudentsAfterSave),
                () -> assertEquals(numberOfStudentsBefore, numberOfStudentsAfterDelete)
        );
    }

    @Test
    void testFindAllHomework() {
    }

    @Test
    void testFindAllGrades() {
    }

    @Test
    void testSaveStudent() {
        Student student1 = new Student("26","Jancsi",555);
        int result = service.saveStudent(student1.getID(),student1.getName(),student1.getGroup());
        service.deleteStudent(student1.getID());
        assertEquals(1,result);
    }

    @Test
    @DisplayName("Homework saving")
    void testSaveHomework() {
        Homework hw = new Homework("245","Never forget", 13,10);
        int result = service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        assertEquals(1, result);
        service.deleteHomework(hw.getID());
    }

    @Test
    void testSaveGrade() {

    }

    @Test
    void testDeleteStudent() {
        Student student1 = new Student("26","Jancsi",555);
        int result = service.saveStudent(student1.getID(),student1.getName(),student1.getGroup());
        assertEquals(1,result);

        result = service.deleteStudent(student1.getID());
        assertEquals(0,result);
    }

    @Test
    void testDeleteHomework() {
        Homework hw = new Homework("245","Never forget", 13,10);
        int result = service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        assertEquals(1, result);

        result = service.deleteHomework(hw.getID());
        assertEquals(0,result);
    }

    @Test
    void testUpdateStudent() {
        Student student1 = new Student("26","Jancsi",555);
        service.saveStudent(student1.getID(),student1.getName(),student1.getGroup());
        int result = service.updateStudent(student1.getID(),"Jancsika", 551);
        assertEquals(1,result);

        service.deleteStudent(student1.getID());

    }

    @Test
    void testUpdateHomework() {
        Homework hw = new Homework("245","Never forget", 13,10);
        int result = service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        assertEquals(1, result);
        result = service.updateHomework(hw.getID(),"Never gonna give you up",hw.getDeadline(), hw.getStartline());
        assertEquals(1, result);
    }

    @Test
    void testExtendDeadline() {
    }

    @Test
    void testCreateStudentFile() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"yahman", "534","22","@2","A2"})
    public void testHomeworkIDs(String id){
        Homework  hw = new Homework(id,"TheOneTrueDescription", 10,5);
        int res = service.saveHomework(hw.getID(),hw.getDescription(), hw.getDeadline(),hw.getStartline());
        service.deleteHomework(hw.getID());
        assertEquals(1,res);

    }
}