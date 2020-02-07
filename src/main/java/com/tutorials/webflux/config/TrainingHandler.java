package com.tutorials.webflux.config;

import com.tutorials.webflux.model.Training;
import com.tutorials.webflux.repository.TrainingRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;
import java.time.Duration;

@Component
public class TrainingHandler {
    private final TrainingRepository trainingRepository;

    public TrainingHandler(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public Mono<ServerResponse> getTrainings(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(trainingRepository.findAll().delayElements(Duration.ofSeconds(5)), Training.class);
    }
}
