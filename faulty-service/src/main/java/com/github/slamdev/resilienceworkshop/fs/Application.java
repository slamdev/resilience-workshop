package com.github.slamdev.resilienceworkshop.fs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api")
@SpringBootApplication
public class Application {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("slow-error-response")
    public void slowResponseWithError(HttpServletResponse response) throws InterruptedException {
        LOGGER.info("{} is called", "slow-error-response");
        TimeUnit.SECONDS.sleep(50);
        response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
    }

    @GetMapping("random-error-response")
    public void responseWithRandomError(HttpServletResponse response) throws InterruptedException {
        LOGGER.info("{} is called", "random-error-response");
        if (RANDOM.nextBoolean()) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
