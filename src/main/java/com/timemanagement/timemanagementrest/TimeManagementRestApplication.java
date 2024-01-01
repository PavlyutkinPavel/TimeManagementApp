package com.timemanagement.timemanagementrest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "DB Lab6 Project",
                description = "This is Java Spring Boot REST project for Time Management App with many features",
                contact = @Contact(
                        name = "Tsikhanionak Ilya",
                        email = "ilya@gmail.com",
                        url = "@ilya"
                )

        )
)
@SpringBootApplication
public class TimeManagementRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeManagementRestApplication.class, args);
    }

}
