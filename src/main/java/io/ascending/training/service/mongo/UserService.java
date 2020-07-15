package io.ascending.training.service.mongo;

import io.ascending.training.model.mongoModel.MongoUser;
import io.ascending.training.repository.interfaces.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public MongoUser findByName(String name) {
        return userRepository.findByName(name);
    }

    public MongoUser save(MongoUser user) {
        return userRepository.save(user);
    }

    public MongoUser insert(MongoUser user) {
        return userRepository.insert(user);
    }

    public boolean deleteByName(String name) {
        return userRepository.deleteByName(name);
    }

    public MongoUser update(MongoUser user) {
        return userRepository.save(user);
    }

    public MongoUser findById(String id){
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
