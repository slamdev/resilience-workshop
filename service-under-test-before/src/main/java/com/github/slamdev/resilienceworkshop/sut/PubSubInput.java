package com.github.slamdev.resilienceworkshop.sut;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PubSubInput {
    String INPUT = "sample-input";

    @Input(INPUT)
    SubscribableChannel pubSubInput();
}
