package com.github.slamdev.resilienceworkshop.sut;

import com.mongodb.MongoClientOptions;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.mongodb.MongoMetricsCommandListener;
import io.micrometer.core.instrument.binder.mongodb.MongoMetricsConnectionPoolListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoClientOptionsConfiguration {

    @Bean
    public MongoClientOptions mongoClientOptions(MeterRegistry meterRegistry) {
        return MongoClientOptions.builder()
                .addCommandListener(new MongoMetricsCommandListener(meterRegistry))
                .addConnectionPoolListener(new MongoMetricsConnectionPoolListener(meterRegistry))
                .build();
    }
}
