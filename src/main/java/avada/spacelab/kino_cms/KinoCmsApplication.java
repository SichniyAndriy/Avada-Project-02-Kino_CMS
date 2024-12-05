package avada.spacelab.kino_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KinoCmsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KinoCmsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KinoCmsApplication.class);
    }
}
