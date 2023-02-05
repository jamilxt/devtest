package co.hishab.devtest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties
@EnableAsync
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class DevTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevTestApplication.class, args);
    }

}
