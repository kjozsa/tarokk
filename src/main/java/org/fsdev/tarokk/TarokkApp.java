package org.fsdev.tarokk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TarokkApp {
    public static void main(String[] args) {
        SpringApplication.run(TarokkApp.class, args);
    }
}
