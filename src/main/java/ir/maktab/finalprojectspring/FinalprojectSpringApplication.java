package ir.maktab.finalprojectspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class FinalprojectSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalprojectSpringApplication.class, args);
    }

}
