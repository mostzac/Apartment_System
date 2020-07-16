package io.ascending.training.service.mongo;

import io.ascending.training.model.mongoModel.MongoUser;
import io.ascending.training.repository.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Qualifier("mongoService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public MongoUser findByName(String name) {
        return userRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    public MongoUser save(MongoUser user) {
        return userRepository.save(user);
    }

    public MongoUser insert(MongoUser user) {
        return userRepository.insert(user);
    }

    public boolean deleteByName(String name) {
        return userRepository.deleteByName(name)==1?true:false;
    }

    public MongoUser update(MongoUser user) {
        return userRepository.save(user);
    }

    public MongoUser findById(String id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Boolean existById(String id) {
        return userRepository.existsById(id);
    }

    public List<MongoUser> findAll() {
        return userRepository.findAll();
    }

//
//    List<MongoUser> getUsers();
//
//    boolean save(MongoUser user);
//
//    boolean update(MongoUser user);
//
//    boolean deleteUserByName(String name);
//
//    boolean deleteUserById(String id);
}
