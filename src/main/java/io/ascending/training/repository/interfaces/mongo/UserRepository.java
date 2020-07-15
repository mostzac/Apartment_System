package io.ascending.training.repository.interfaces.mongo;

import io.ascending.training.model.mongoModel.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<MongoUser,String> {
    MongoUser findByName(String name);
//    MongoUser findById(String id);
//    List<MongoUser> getUsers();
//    boolean save(MongoUser user);
//    boolean update(MongoUser user);
    boolean deleteByName(String name);
//    boolean deleteById(String id);
}
