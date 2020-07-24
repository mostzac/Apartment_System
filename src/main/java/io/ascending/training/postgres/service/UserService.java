package io.ascending.training.postgres.service;

import com.mongodb.MongoWriteException;
import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.repository.UserRepository;
import io.ascending.training.postgres.model.Role;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.repository.interfaces.RoleDAO;
import io.ascending.training.postgres.repository.interfaces.UserDAO;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("postgresService")
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private io.ascending.training.mongo.service.UserService userService;


    public boolean save(User u) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleDAO.getRoleByName("MongoUser"));
        u.setRoles(roles);

        MongoUser user = userRepository.findByName(u.getAccount()).orElse(new MongoUser(u.getAccount(), 0));
        userRepository.save(user);

        return (userDAO.save(u));
    }

    public boolean updateUserOptionalApartment(User u) {
        String originalAccount = userDAO.getUserById(u.getId()).getAccount();

        try {
            MongoUser user = userRepository.findByName(originalAccount).orElse(new MongoUser(u.getAccount(), 0));
            user.setName(u.getAccount());
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            return false;
        }
        u.setRoles(userDAO.getUserById(u.getId()).getRoles());
        return (userDAO.update(u));
    }

    public boolean updateUserRoles(User u) {
        String originalAccount = userDAO.getUserById(u.getId()).getAccount();

        try {
            MongoUser user = userRepository.findByName(originalAccount).orElse(new MongoUser(u.getAccount(), 0));
            user.setName(u.getAccount());
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            return false;
        }
        return (userDAO.update(u));
    }

//    public boolean delete(User u){ return (userDAO.delete(u));}

    public boolean deleteUserByAccount(String userAccount) {
        userRepository.deleteByName(userAccount);
        return (userDAO.deleteUserByAccount(userAccount));
    }

    public boolean deleteUserById(long id) {
        userRepository.deleteByName(userDAO.getUserById(id).getAccount());
        return userDAO.deleteUserById(id);
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    public User getUserByAccount(String userAccount) {
        return userDAO.getUserByAccount(userAccount);
    }

    public User getUserById(long uid) {
        return userDAO.getUserById(uid);
    }

    public User getUserByCredential(String userAccount, String password) {
        return userDAO.getUserByCredential(userAccount, password);
    }
}
