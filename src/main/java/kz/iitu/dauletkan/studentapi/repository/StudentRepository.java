package kz.iitu.dauletkan.studentapi.repository;

import kz.iitu.dauletkan.studentapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
