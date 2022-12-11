package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student",
                        String.class))
                .isNotNull();
    }

    @Test
    void testGetStudentById() throws Exception {
        ArrayList arrayList = (ArrayList) studentService.getAllStudents();
        if (arrayList.size() == 0) {
            throw new Exception("список студентов пуст");
        }

        Student student = (Student) arrayList.get(0);

        String responce = this.testRestTemplate.getForObject("http://localhost:" + port + "/student/" +
                student.getId(), String.class);
        JSONObject jsonObject = new JSONObject(responce);

        Assertions.assertThat(jsonObject.getLong("id")).isEqualTo(student.getId());
        Assertions.assertThat(jsonObject.getString("name")).isEqualTo(student.getName());
        Assertions.assertThat(jsonObject.getInt("age")).isEqualTo(student.getAge());
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("testName1");
        student.setAge(30);

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student/",
                        student, String.class))
                .isNotBlank();
    }

    @Test
    void testGetStudentsByAge() throws Exception{
        int ageQuery = 20;

        String responceStr = this.testRestTemplate.getForObject("http://localhost:" + port + "/student/filter?age="
                + ageQuery, String.class);
        JSONArray jsonArray = new JSONArray(responceStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            Assertions.assertThat(jsonArray.getJSONObject(i).get("age"))
                    .isEqualTo(ageQuery);
        }
    }


}
