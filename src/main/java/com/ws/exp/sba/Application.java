package com.ws.exp.sba;

import com.ws.exp.sba.model.Reader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("dsc")
    public void getUserDetails() {
        Reader reader = new Reader();
        reader.setUsername("dsc");
        reader.setFullname("DDDsc");
        reader.setPassword("{noop}password");
    }
}
