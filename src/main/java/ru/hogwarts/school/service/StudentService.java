package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {
    /*Map<Long, Student> students = new HashMap<>();
    private Long idCounter = 1L;*/

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    private Object flagPrintConsole = new Object(); // для синхронизации потоков

    public StudentService(StudentRepository studentRepository) {
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

    public List<String> getStudentsNamesStartWithA() {
        return studentRepository.findAll().stream()
                .map(s->s.getName().toUpperCase())
                .filter(name->name.startsWith("A"))
                .sorted().collect(Collectors.toList());
    }

    public Float getStudentAverageAgeUsingStream() {
        List <Student> listStudents = studentRepository.findAll();
        return (float)listStudents.stream().mapToInt(s->s.getAge()).sum() / listStudents.size();
    }

    public Integer getFunctionResult() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1).limit(1_000_000).reduce(0, (a, b) -> a + b );
        System.out.println("Вычисление 1-м потоком: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        sum = Stream.iterate(1, a -> a +1).limit(1_000_000).parallel().reduce(0, (a, b) -> a + b );
        System.out.println("Вычисление параллельными потоками: " + (System.currentTimeMillis() - start));

        return sum;
    }

    public void printStudentsUsingThread() {
        List <Student> students = studentRepository.findAll();
        if (students.size() >= 6) {
            printIntoConsole(students.get(0).getName());
            printIntoConsole(students.get(1).getName());

            new Thread(
                    ()->{
                        printIntoConsole(students.get(2).getName());
                        printIntoConsole(students.get(3).getName());
                    }
            ).start();

            new Thread(
                    ()->{
                        printIntoConsole(students.get(4).getName());
                        printIntoConsole(students.get(5).getName());
                    }
            ).start();
        }
    }

    private void printIntoConsole(String name) {
        hardLongProcess();

        System.out.println(name);
    }

    public void printStudentsUsingThreadAndSyncMethod() {
        List <Student> students = studentRepository.findAll();
        if (students.size() >= 6) {
            printIntoConsoleUsingSyncMethod(students.get(0).getName());
            printIntoConsoleUsingSyncMethod(students.get(1).getName());

            new Thread(
                    ()->{
                        printIntoConsoleUsingSyncMethod(students.get(2).getName());
                        printIntoConsoleUsingSyncMethod(students.get(3).getName());
                    }
            ).start();

            new Thread(
                    ()->{
                        printIntoConsoleUsingSyncMethod(students.get(4).getName());
                        printIntoConsoleUsingSyncMethod(students.get(5).getName());
                    }
            ).start();
        }
    }

    private synchronized void printIntoConsoleUsingSyncMethod(String name) {
        hardLongProcess();

        System.out.println(name);
    }

    public void printStudentsUsingThreadWithFlag() {
        List <Student> students = studentRepository.findAll();
        if (students.size() >= 6) {
            printIntoConsoleUsingSyncFlag(students.get(0).getName());
            printIntoConsoleUsingSyncFlag(students.get(1).getName());

            new Thread(
                    ()->{
                        printIntoConsoleUsingSyncFlag(students.get(2).getName());
                        printIntoConsoleUsingSyncFlag(students.get(3).getName());
                    }
            ).start();

            new Thread(
                    ()->{
                        printIntoConsoleUsingSyncFlag(students.get(4).getName());
                        printIntoConsoleUsingSyncFlag(students.get(5).getName());
                    }
            ).start();
        }
    }

    private void printIntoConsoleUsingSyncFlag(String name) {
        hardLongProcess();

        synchronized (flagPrintConsole) {
            System.out.println(name);
        }
    }

    private void hardLongProcess() {
        // Блок кода для демонстрации параллельности выполнения (трудоемкая операция)
        String s = "";
        for (int i = 0; i < 100_000; i++) {
            s += i;
        }
    }
}
