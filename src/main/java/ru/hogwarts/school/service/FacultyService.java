package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService{

    //@Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (getFacultyById(faculty.getId()) == null) {
            return null;
        }else {
            return facultyRepository.save(faculty);
        }
    }

    public Faculty deleteFacultyById(Long id) {
        Faculty faculty = getFacultyById(id);
        if (faculty == null) {
            return null;
        }else {
            facultyRepository.deleteById(id);
            return faculty;
        }
    }

    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }
}