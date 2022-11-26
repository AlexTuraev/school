package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService{
    Map<Long, Faculty> faculties = new HashMap<>();
    private Long idCounter = 1L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(idCounter);
        faculties.put(idCounter++, faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        } else{
            return null;
        }

    }

    public Faculty deleteFacultyById(Long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    public Collection <Faculty> getFacultiesByColor(String color) {
        return faculties.values().stream().filter((faculty)->faculty.getColor().equals(color)).collect(Collectors.toList());
    }
}