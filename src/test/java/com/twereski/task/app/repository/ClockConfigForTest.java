package com.twereski.task.app.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Profile("test")
@Configuration
public class ClockConfigForTest {

    @Bean
    @Primary
    public Clock clockFixed() {
        return Clock.fixed(Instant.parse("2019-03-03T16:15:30Z"), ZoneId.systemDefault());
    }
}

