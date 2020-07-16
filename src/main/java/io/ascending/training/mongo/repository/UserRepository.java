package io.ascending.training.mongo.repository;

import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.util.Streamable;

import java.util.Optional;

public interface UserRepository extends MongoRepository<MongoUser, String> {
    Optional<MongoUser> findByName(String name);

    @Query(value="{'message':{'$ref':'messages','$id':?0}}",fields="{'_id':0}",sort="{'age':-1}")
    Streamable<MongoUser> findAllByMessageContainsContent(String id);


    //Containing on Collection
    MongoUser findByMessageContaining(MongoMessage message);
    MongoUser findByMessageContains(MongoMessage message);

    //    MongoUser findById(String id);
//    List<MongoUser> getUsers();
//    boolean save(MongoUser user);
//    boolean update(MongoUser user);
    @DeleteQuery("{'name':?0}")
    long deleteByName(String name);
//    boolean deleteById(String id);
}
