package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    /*Map<Long, Student> students = new HashMap<>();
    private Long idCounter = 1L;*/

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        if (getStudentById(student.getId()) == null) {
            return null;
        } else {
            return studentRepository.save(student);
        }
    }

    public Student deleteStudentById(Long id) {
        Student student = getStudentById(id);
        if (student == null) {
            return null;
        } else {
            studentRepository.deleteById(id);
            return student;
        }
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection <Student> getStudentsByAge(int age) {
        return studentRepository.findAll().stream().filter((student)->student.getAge() == age).collect(Collectors.toList());
    }
}
