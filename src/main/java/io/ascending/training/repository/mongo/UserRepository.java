package io.ascending.training.repository.mongo;

import io.ascending.training.model.mongoModel.MongoUser;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<MongoUser, String> {
    Optional<MongoUser> findByName(String name);

    //    MongoUser findById(String id);
//    List<MongoUser> getUsers();
//    boolean save(MongoUser user);
//    boolean update(MongoUser user);
    @DeleteQuery("{'name':?0}")
    long deleteByName(String name);
//    boolean deleteById(String id);
}
