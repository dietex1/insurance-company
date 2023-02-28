package TJV.fediayar_tjv_semestral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication // Start class of spring boot application // spring app won't start
@Configuration
@ComponentScan({"TJV.fediayar_tjv_semestral.converter", "TJV.fediayar_tjv_semestral.service", "TJV.fediayar_tjv_semestral.controller"})
@EntityScan(basePackages = "TJV.fediayar_tjv_semestral.domain")
@EnableJpaRepositories(basePackages = "TJV.fediayar_tjv_semestral.repository")
public class FediayarTjvSemestralApplication {

    public static void main(String[] args) {
        SpringApplication.run(FediayarTjvSemestralApplication.class, args);
    }

}


