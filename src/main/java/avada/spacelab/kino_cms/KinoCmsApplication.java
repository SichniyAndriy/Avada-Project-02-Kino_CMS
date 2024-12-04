package avada.spacelab.kino_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KinoCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KinoCmsApplication.class, args);
    }

}
