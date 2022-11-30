package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequestMapping("faculty")
@RestController
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping("{id}")
    public ResponseEntity getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty);
        if (updatedFaculty == null) {
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); исправил статус
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(faculty);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.deleteFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(faculty);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@RequestParam("color") String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }
}
