package io.ascending.training.mongo.repository;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.query.MongoParameterAccessor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserRepository extends MongoRepository<MongoUser, String> {

    Optional<MongoUser> findByName(String name);

    //        @Query(value="{'message.content':?0}",fields="{'_id':0}",sort="{'age':-1}")
    default List<MongoUser> findAllByMessageContainsContent(String content) {
        List<MongoUser> list = findAll().stream().filter(user -> user.getMessage()!=null&&user.getMessage().getContent().equals(content)).collect(Collectors.toList());
        return list;
    }

//    @Query(value="{'message._id':?0,'message.content':?1}",fields="{'_id':0}",sort="{'age':-1}")
//    Streamable<MongoUser> findAllByMessageContainsContent(String id,String content);


    //Containing on Collection
    default List<MongoUser> findAllByMessageContainingByMessageContaining(MongoMessage message) {
        return findAll().stream().filter(user -> user.getMessage() != null && user.getMessage().getId().equals(message.getId())).collect(Collectors.toList());
    }

//    MongoUser findAllByMessageContainsByMessageContains(MongoMessage message);

    //    MongoUser findById(String id);
//    List<MongoUser> getUsers();
//    boolean save(MongoUser user);
//    boolean update(MongoUser user);
    @DeleteQuery("{'name':?0}")
    long deleteByName(String name);
//    boolean deleteById(String id);
}
