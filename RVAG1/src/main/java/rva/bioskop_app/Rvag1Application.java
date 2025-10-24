package rva.bioskop_app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "rva")
@EnableJpaRepositories("rva.repository")
@EntityScan("rva.models")
public class Rvag1Application {
    public static void main(String[] args) {
        SpringApplication.run(Rvag1Application.class, args);
    }
}
