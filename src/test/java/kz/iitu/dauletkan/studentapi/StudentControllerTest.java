package kz.iitu.dauletkan.studentapi;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kz.iitu.dauletkan.studentapi.model.Student;
import kz.iitu.dauletkan.studentapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository repository;

    private Student nursultan;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        nursultan = repository.save(new Student("Nursultan", "Dauletkan", "nursultan.dauletkan@student.iitu.kz", "IT2-2313"));
    }

    @Test
    void infoShowsStudentName() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student").value("Dauletkan Nursultan"))
                .andExpect(jsonPath("$.studentId").value("37037"));
    }

    @Test
    void createStudent() throws Exception {
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Aigerim\",\"lastName\":\"Test\",\"email\":\"aigerim.test@student.iitu.kz\",\"groupName\":\"IT2-2313\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Aigerim"));
    }

    @Test
    void findAllStudents() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastName").value("Dauletkan"));
    }

    @Test
    void findStudentById() throws Exception {
        mockMvc.perform(get("/api/students/" + nursultan.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Nursultan"));
    }

    @Test
    void updateStudent() throws Exception {
        mockMvc.perform(put("/api/students/" + nursultan.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Nursultan\",\"lastName\":\"Dauletkan\",\"email\":\"nursultan.dauletkan@student.iitu.kz\",\"groupName\":\"IT2-2313 (DevOps)\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("IT2-2313 (DevOps)"));
    }

    @Test
    void updateUnknownStudentReturns404() throws Exception {
        mockMvc.perform(put("/api/students/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"X\",\"lastName\":\"Y\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete("/api/students/" + nursultan.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/students/" + nursultan.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void unknownStudentReturns404() throws Exception {
        mockMvc.perform(get("/api/students/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Student with id 99999 not found"));
    }
}
