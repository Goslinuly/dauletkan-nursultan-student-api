package kz.iitu.dauletkan.studentapi.config;

import kz.iitu.dauletkan.studentapi.model.Student;
import kz.iitu.dauletkan.studentapi.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Seeds individual test data on the first start (empty database only)
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final StudentRepository repository;

    public DataInitializer(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Student("Nursultan", "Dauletkan", "nursultan.dauletkan@student.iitu.kz", "IT2-2313"));
            log.info("Seeded test data for student Dauletkan Nursultan (ID 37037)");
        }
    }
}
