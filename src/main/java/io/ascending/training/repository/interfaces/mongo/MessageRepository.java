package io.ascending.training.repository.interfaces.mongo;

import io.ascending.training.model.mongoModel.MongoMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<MongoMessage,String> {
}
