package com.tutorials.webflux.controller;

import com.tutorials.webflux.model.Training;
import com.tutorials.webflux.repository.TrainingRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
class TrainingControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TrainingRepository trainingRepository;

    @Before
    public void setUp() {
        webTestClient = webTestClient
                            .mutate()
                            .responseTimeout(Duration.ofSeconds(50))
                            .build();
    }

    @Test
    public void trainingTest() {
        trainingRepository.findAll().subscribe(training -> System.out.println("[TRAINING TEST] " + training.getName()));

        Flux<Training> trainingStreamFlux = webTestClient.get().uri("/trainings")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Training.class)
                .getResponseBody();

        StepVerifier.create(trainingStreamFlux)
                .expectNextMatches(training -> training.getName().equals("JAVA"))
                .expectNextMatches(training -> training.getName().equals("SPRING"))
                .thenCancel()
                .verify();
    }

    @Test
    public void trainingsTest() {
        WebClient.create("http://localhost:7774")
                .get()
                .uri("/trainings")
                .retrieve()
                .bodyToFlux(Training.class)
                .doOnNext(training -> System.out.println("[training]: " + training.getName()))
                .blockLast();
    }
}