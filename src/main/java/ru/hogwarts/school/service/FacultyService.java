package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
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

    public List<Faculty> getFacultiesByColorOrName(String param) {
        List <Faculty> facultyList = facultyRepository.findByColorIgnoreCase(param);
        facultyList.addAll(facultyRepository.findByNameIgnoreCase(param));

        Set <Faculty> facultySet = new HashSet<>(facultyList); // чтобы избежать дублей, если имя и цвет совпадают
        return facultySet.stream().toList();
    }

    public List<Student> getStudentsByIdOfFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id).orElse(null);

        if (faculty == null) {
            return null;
        } else {
            return faculty.obtainStudentsList();
        }
    }
}