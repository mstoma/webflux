package com.tutorials.webflux.controller;

import com.tutorials.webflux.model.Training;
import com.tutorials.webflux.repository.TrainingRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.time.Duration;

@RestController
public class TrainingController {
    public final TrainingRepository trainingRepository;

    public TrainingController(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @GetMapping(value = "/trainings", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Training> getBooks() {
        return trainingRepository.findAll().delayElements(Duration.ofSeconds(1));
    }
}
