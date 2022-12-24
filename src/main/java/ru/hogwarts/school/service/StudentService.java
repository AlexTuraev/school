package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    /*Map<Long, Student> students = new HashMap<>();
    private Long idCounter = 1L;*/

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("The method {} was called", "<<createStudent>>");
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        logger.info("The method {} was called. Getting record student with id = {}", "<<getStudentById>>", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) {
        logger.info("The method {} was called", "<<updateStudent>>");
        if (getStudentById(student.getId()) == null) {
            return null;
        } else {
            return studentRepository.save(student);
        }
    }

    public Student deleteStudentById(Long id) {
        logger.info("The method {} was called. Deleting student's record with id = {}", "<<deleteStudentById>>", id);
        Student student = getStudentById(id);
        if (student == null) {
            return null;
        } else {
            studentRepository.deleteById(id);
            return student;
        }
    }

    public Collection<Student> getAllStudents() {
        logger.info("The method {} was called", "<<getAllStudents>>");
        return studentRepository.findAll();
    }

    public List<Student> findByAge (int age) {
        //System.out.println("age = " + age);
        logger.info("The method {} was called", "<<findByAge>>");
        return studentRepository.findByAge(age);
    }

    public List<Student> getStudentsByAgeBetweenMinMax(int min, int max) {
        logger.info("The method {} was called", "<<getStudentsByAgeBetweenMinMax>>");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByIdOfStudent(Long id) {
        logger.info("The method {} was called", "<<getFacultyByIdOfStudent>>");
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return null;
        } else {
            return student.obtainFaculty();
        }
    }

    public Integer getCountOfStudents() {
        logger.info("The method {} was called", "<<getFacultyByIdOfStudent>>");
        return studentRepository.getCountOfStudents();
    }

    public Float getStudentsAverageAge() {
        logger.info("The method {} was called", "<<getStudentsAverageAge>>");
        return studentRepository.getStudentsAverageAge();
    }

    public List<Student> getLast5Students() {
        logger.info("The method {} was called", "<<getLast5Students>>");
        return studentRepository.getLast5Students();
    }
}
