package org.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "org.example.messages_application",
        "org.example.messages_infrastructure",
        "org.example.notifications",
        "org.example.user_infrastructure",
        "org.example.user_application",
})
@EnableJpaRepositories(basePackages= {
        "org.example.user_infrastructure",
        "org.example.messages_infrastructure",
        "org.example.notifications",
})
@ComponentScan(basePackages = {
        "org.example.messages_application",
        "org.example.messages_infrastructure",
        "org.example.notifications",
        "org.example.user_infrastructure",
        "org.example.user_application",
})
@EntityScan("org.example.**")
@EnableAspectJAutoProxy
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
