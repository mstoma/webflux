package com.tutorials.webflux.repository;

import com.tutorials.webflux.model.Training;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends ReactiveMongoRepository<Training, String> {
}
