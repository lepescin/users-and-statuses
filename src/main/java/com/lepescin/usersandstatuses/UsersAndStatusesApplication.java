package com.lepescin.usersandstatuses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class UsersAndStatusesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersAndStatusesApplication.class, args);
    }

}
