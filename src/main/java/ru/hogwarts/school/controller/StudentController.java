package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import ru.hogwarts.school.model.HogwardsItem;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequestMapping("student")
@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.ok(student);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeStudent(@PathVariable Long id) {
        Student student = studentService.deleteStudentById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents()) ;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam("age") int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/agebetween")
    public ResponseEntity<List<Student>> getStudentsByAgeBetweenMinMax(
            @RequestParam("min") int min,
            @RequestParam("max") int max) {
        return ResponseEntity.ok(studentService.getStudentsByAgeBetweenMinMax(min, max));
    }

    @GetMapping("/getfaculty/{id}")
    public ResponseEntity <Faculty> getFacultyByIdOfStudent(@PathVariable Long id){
        Faculty faculty = studentService.getFacultyByIdOfStudent(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(faculty);
        }
    }

    @GetMapping("/count-of-students")
    public ResponseEntity <Integer> getCountOfStudents() {
        return ResponseEntity.ok().body(studentService.getCountOfStudents());
    }

    @GetMapping("/avg-age")
    public ResponseEntity <Float> getStudentsAverageAge() {
        return ResponseEntity.ok().body(studentService.getStudentsAverageAge());
    }

    @GetMapping("/get-last-five")
    public ResponseEntity <List<Student>> getLast5Students() {
        return ResponseEntity.ok().body(studentService.getLast5Students());
    }

    @GetMapping("/get-names-start-a")
    public ResponseEntity<List<String>> getStudentsNamesStartWithA() {
        return ResponseEntity.ok(studentService.getStudentsNamesStartWithA());
    }

    public ResponseEntity<Float> getStudentAverageAgeUsingStream() {
        return ResponseEntity.ok(studentService.getStudentAverageAgeUsingStream());
    }
}
