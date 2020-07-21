package com.github.slamdev.resilienceworkshop.sut;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "faulty-service", url = "${faulty-service.url}")
public interface FaultyServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/slow-error-response")
    void slowResponseWithError();

    @RequestMapping(method = RequestMethod.GET, value = "/api/random-error-response")
    void responseWithRandomError();
}
