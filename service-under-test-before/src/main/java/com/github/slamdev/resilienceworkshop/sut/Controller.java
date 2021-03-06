package com.github.slamdev.resilienceworkshop.sut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;

@RestController
@RequestMapping("api")
@EnableBinding(PubSubInput.class)
public class Controller {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private final FaultyServiceClient faultyServiceClient;

    private final DbRepository repository;

    public Controller(FaultyServiceClient faultyServiceClient, DbRepository repository) {
        this.faultyServiceClient = faultyServiceClient;
        this.repository = repository;
    }

    @GetMapping("usual")
    public void usual(HttpServletResponse response) {
        LOGGER.info("{} is called", "usual");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("delayed-error")
    public void delayedError(HttpServletResponse response) {
        LOGGER.info("{} is called", "delayed-error");
        faultyServiceClient.slowResponseWithError();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("random-error")
    public void randomError(HttpServletResponse response) {
        LOGGER.info("{} is called", "random-error");
        faultyServiceClient.responseWithRandomError();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("slow-db-query")
    public void slowDbQuery(HttpServletResponse response) {
        LOGGER.info("{} is called", "slow-db-query");
        repository.findAll(QDbRepository_Sample.sample.name.notEqualsIgnoreCase(""), PageRequest.of(0, 100));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @StreamListener(PubSubInput.INPUT)
    public void onPubSubMessage(Message<String> message) {
        LOGGER.info("{} is called", "onPubSubMessage");
        if (RANDOM.nextBoolean()) {
            throw new IllegalStateException("something went wrong");
        }
    }
}
