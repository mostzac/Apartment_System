package io.ascending.training.mongo.repository;

import io.ascending.training.mongo.model.MongoMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MessageRepository extends MongoRepository<MongoMessage,String> {
    Optional<MongoMessage> findByContent(String content);
}
