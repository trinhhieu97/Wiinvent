package wiinvent.com.vn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableScheduling
public class WiinventApplication {
    public static void main(String[] args) {
        SpringApplication.run(WiinventApplication.class, args);
    }
}