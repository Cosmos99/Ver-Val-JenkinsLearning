package validation;

import domain.Grade;
import domain.Homework;
import domain.Pair;
import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import service.Service;

import static org.junit.jupiter.api.Assertions.*;

class GradeValidatorTest {

    public static Validator<Grade> gradeValidator;

    @BeforeAll
    public static void setUp() {
        gradeValidator = new GradeValidator();
    }

    @Test
    void validateTest() {
        assertThrows(ValidationException.class, () -> {
           gradeValidator.validate(new Grade(new Pair<>("1","1"), -25.65, 10, "ERROR"));
        });
        assertDoesNotThrow(() -> {
            gradeValidator.validate(new Grade(new Pair<>("1","1"), 7.7, 10, "ERROR"));
        });
    }
}