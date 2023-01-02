package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService{

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    //@Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("The method {} was called", "<<createFaculty>>");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("The method {} was called", "<<getFacultyById>>");
        Faculty f = facultyRepository.findById(id).orElse(null);
        //return facultyRepository.findById(id).orElse(null);
        return f;
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("The method {} was called", "<<updateFaculty>>");
        if (getFacultyById(faculty.getId()) == null) {
            return null;
        }else {
            return facultyRepository.save(faculty);
        }
    }

    public Faculty deleteFacultyById(Long id) {
        logger.info("The method {} was called", "<<deleteFacultyById>>");
        Faculty faculty = getFacultyById(id);
        if (faculty == null) {
            return null;
        }else {
            facultyRepository.deleteById(id);
            return faculty;
        }
    }

    public List<Faculty> findByColor(String color) {
        logger.info("The method {} was called", "<<findByColor>>");
        return facultyRepository.findByColor(color);
    }
    public Collection<Faculty> getAllFaculties() {
        logger.info("The method {} was called", "<<getAllFaculties>>");
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultiesByColorOrName(String param) {
        logger.info("The method {} was called", "<<getFacultiesByColorOrName>>");
        List <Faculty> facultyList = facultyRepository.findByColorIgnoreCase(param);
        facultyList.addAll(facultyRepository.findByNameIgnoreCase(param));

        Set <Faculty> facultySet = new HashSet<>(facultyList); // чтобы избежать дублей, если имя и цвет совпадают
        return facultySet.stream().toList();
    }

    public List<Student> getStudentsByIdOfFaculty(Long id) {
        logger.info("The method {} was called", "<<getStudentsByIdOfFaculty>>");
        Faculty faculty = facultyRepository.findById(id).orElse(null);

        if (faculty == null) {
            return null;
        } else {
            return faculty.obtainStudentsList();
        }
    }

    public Optional<String> getLongestFacultyName() {
        Comparator <String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length()-o1.length();
            }
        };

        List <String> names = facultyRepository.findAll().stream()
                .map(s->s.getName())
                .sorted(comparator).collect(Collectors.toList());
        if (names.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(names.get(0));
        }
    }
}