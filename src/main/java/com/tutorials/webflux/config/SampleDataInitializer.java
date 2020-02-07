package com.tutorials.webflux.config;

import com.tutorials.webflux.model.Training;
import com.tutorials.webflux.repository.TrainingRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private TrainingRepository trainingRepository;

    public SampleDataInitializer(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    // This will propagate data to db (in this case two records - JAVA and SPRING with random UUIDs)
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Flux<Training> trainingFlux = trainingRepository
                .findAll()
                .thenMany(
                        Flux
                            .just("JAVA", "SPRING")
                            .map(name -> new Training(UUID.randomUUID().toString(), name))
                            .flatMap(trainingRepository::save)
                );
        Mono<Void> all = Mono.when(trainingFlux);
        all.block();

        trainingRepository.findAll().subscribe(training -> System.out.println(training.getName()));
    }
}
