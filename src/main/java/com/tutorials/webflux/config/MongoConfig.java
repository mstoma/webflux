package com.tutorials.webflux.config;

import com.tutorials.webflux.repository.TrainingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = TrainingRepository.class)
public class MongoConfig {
    @Bean
    public RouterFunction<ServerResponse> booksRoute(TrainingHandler trainingHandler) {
        return RouterFunctions.route(GET("/trainings-handler"), trainingHandler::getTrainings);
    }
}
