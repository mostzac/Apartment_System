package io.ascending.training.mongo.service;

import com.mongodb.MongoWriteException;
import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.repository.MessageRepository;
import io.ascending.training.mongo.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("mongoService")
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private Logger logger;

    public MongoUser findByName(String name) {
        return userRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    public MongoUser save(MongoUser user) {
//        try {
            return userRepository.save(user);
//        } catch (DuplicateKeyException e) {
//            user.setMessage(messageRepository.findByContent(user.getMessage().getContent()).get());
//            return userRepository.save(user);
//        }
    }

    public MongoUser insert(MongoUser user) {
//        try {
            return userRepository.insert(user);
//        } catch (DuplicateKeyException e) {
//            user.setMessage(messageRepository.findByContent(user.getMessage().getContent()).get());
//            return userRepository.insert(user);
//        }
    }

    public boolean deleteByName(String name) {
        return userRepository.deleteByName(name)==1?true:false;
    }

    public MongoUser update(MongoUser user) {
//        try {
            return userRepository.save(user);
//        } catch (DuplicateKeyException e) {
//            user.setMessage(messageRepository.findByContent(user.getMessage().getContent()).get());
//            return userRepository.save(user);
//        }
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
